package com.alan.community.service;

import com.alan.community.dto.NotificationDTO;
import com.alan.community.dto.PaginationDTO;
import com.alan.community.enums.NotificationEnum;
import com.alan.community.enums.NotificationTypeEnum;
import com.alan.community.exception.CustomizeErrorCode;
import com.alan.community.exception.CustomizeException;
import com.alan.community.mapper.NotificationMapper;
import com.alan.community.model.Notification;
import com.alan.community.model.NotificationExample;
import com.alan.community.model.User;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author alan
 * @date 2020/3/27 21:19
 */
@Service
public class NotificationService {


    @Autowired
    private  NotificationMapper notificationMapper;
    public PaginationDTO queryAllNotify(PaginationDTO pageDTO, Long id) {
        List<NotificationDTO> notificationDTOS = new ArrayList<>();
        PageHelper.startPage(pageDTO.getCurrentPage(),pageDTO.getPageSize());
        NotificationExample notificationExample = new NotificationExample();
        notificationExample.setOrderByClause("gmt_create desc");
        notificationExample.createCriteria().andReceiverEqualTo(id);
        List<Notification> notifications = notificationMapper.selectByExample(notificationExample);
        if (notifications.size()==0) {
            return pageDTO;
        }
        int total = (int) new PageInfo<>(notifications).getTotal();
        for (Notification notification : notifications) {
            NotificationDTO notificationDTO=new NotificationDTO();
            BeanUtils.copyProperties(notification,notificationDTO);
            notificationDTO.setTypeName(NotificationTypeEnum.nameOf(notification.getType()));
            notificationDTOS.add(notificationDTO);
        }
        pageDTO.setTotalRows(total);
        pageDTO.setData(notificationDTOS);
        return pageDTO;
    }

    public Long unreadCount(Long id) {
        NotificationExample notificationExample = new NotificationExample();
        notificationExample.createCriteria().andReceiverEqualTo(id).andStatusEqualTo(NotificationEnum.UNREAD.getStatus());
        return notificationMapper.countByExample(notificationExample);
    }

    public NotificationDTO read(User currentUser, Long id) {
        Notification notification = notificationMapper.selectByPrimaryKey(id);
        if (notification==null) {
            throw new CustomizeException(CustomizeErrorCode.NOTIFICATION_NOTFOUND);
        }
        if (!Objects.equals(notification.getReceiver(),currentUser.getId())){
            throw new CustomizeException(CustomizeErrorCode.READ_NOTIFICATION_FAIL);

        }
        notification.setStatus(NotificationEnum.READ.getStatus());
        notificationMapper.updateByPrimaryKey(notification);
        NotificationDTO notificationDTO = new NotificationDTO();
        BeanUtils.copyProperties(notification,notificationDTO);
        notificationDTO.setTypeName(NotificationTypeEnum.nameOf(notification.getType()));

        return notificationDTO;
    }
}
