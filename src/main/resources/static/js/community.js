


function like(e) {
    let id = e.getAttribute("data-id");
    $.ajax({
        method:"POST",
        url:"/comment/like",
        contentType:'application/json',
        data:JSON.stringify({'id':id}),
        dataType: "json",
    }).then(r => {
        if (r.code == 200) {
            layer.msg("感谢你的点赞！",{time:1000,icon:6})
        }
    })
}
function commentQuestion() {
     let questionId = $("#questionId").val();
     let content = $("#comment_content").val();
     comment2Target(questionId,content,1)

}
function comment(e) {
    let id = e.getAttribute("data-id");
    let content = $("#input-"+id).val();
    comment2Target(id,content,2)

}
function comment2Target(targetId,content,type) {
    if (!content) {
        layer.msg("评论不能为空！");
        return;
    }
    $.ajax({
        method: "POST",
        url: "/comment",
        contentType:'application/json',
        data: JSON.stringify({'parentId': targetId, 'content': content,'type':type}),
        dataType: "json",
    }).then(r =>{
        if (r.code==200){
            window.location.reload();
        }else if (r.code==2003){
            let confirm = layer.confirm(r.message,{
                    btn: ['登录','取消'] //按钮
                },function (index) {
                    layer.close(index);
                    window.open("https://gitee.com/oauth/authorize?client_id=86e31cde3d5e729da75a2bbfd18c4e0dd069136c8e8191e673ef1cc881ecb037&redirect_uri=http://localhost:8887/callback&response_type=code&scope=user_info");
                    window.localStorage.setItem("closeable","true");
                    window.location.reload();
                },function (index) {
                    layer.close(index);
                }
            );

        }else {
            layer.msg(r.message);
        }
    })
}


function collapseComment(e) {
    let id = e.getAttribute("data-id");
    let comments = $("#comment-" + id);
    let collapse = e.getAttribute("data-collapse");
    if (collapse) {
        //折叠
        comments.removeClass("in");
        e.removeAttribute("data-collapse");
        e.classList.remove('active');
    } else {

        let subCommentContainer = $("#comment-" + id);
        // 展开
        if (subCommentContainer.children().length!=1){
            comments.addClass('in');
            e.setAttribute("data-collapse", "in");
            e.classList.add('active');
        }else {
            $.getJSON("/comment/" + id, function (data) {
                $.each(data.data.reverse(), function (index, comment) {
                    let mediaLeftElement = $("<div/>", {
                        "class": "media-left",
                    }).append($("<img/>", {
                        "class": "media-object img-rounded",
                        "src": comment.user.avatarUrl
                    }));
                    let mediaBodyElement = $("<div/>", {
                        "class": "media-body"
                    }).append($("<h6/>", {
                        "class": 'media-heading',
                        'html': comment.user.name
                    })).append($("<div/>", {
                        'html': comment.content
                    })).append($("<div/>", {
                        'class': 'menu-icon-group'
                    })).append($("<span/>", {
                        'class': 'pull-right',
                        'style': 'color:#999',
                        'html': moment(comment.gmtCreate).format('YYYY-MM-DD H:mm:ss')
                    }));
                    let commentElement = $("<div/>", {
                        "class": "row col-lg-12 col-md-12 col-sm-12 col-xs-12 comment-border media",
                    }).append(mediaLeftElement)
                        .append(mediaBodyElement);
                    subCommentContainer.prepend(commentElement);
                });
                comments.addClass('in');
                e.setAttribute("data-collapse", "in");
                e.classList.add('active');

            })
        }
    }
}
function showTag(e) {
      $("#tagContent").show();
}
function hideTag(e) {
    $("#tagContent").hide();
}
function selectTag(e) {
    let value = e.getAttribute("data-tag");
    let val = $("#tag").val();
    if (val.indexOf(value) == -1) {
        if (val){
            $("#tag").val(val+','+value);
        }else {
            $("#tag").val(value);
        }
    }

}
function publish() {
    let title = $("#title").val();
    let content = $("#content").val();
    let tag = $("#tag").val();
    if (!title|| title == "") {
        layer.msg("标题不能为空");
        return;
    } if (!content || content == "") {
        layer.msg("内容不能为空！");
        return;
    } if (!tag || tag == "") {
        layer.msg("标签不能为空！");
        return;
    }
    $.ajax({
        type: "POST",
        dataType: "json",//预期服务器返回的数据类型
        url: "/publish",
        data: $('#questionForm').serialize(),
    }).then(r => {
        if (r.code == 200) {
            layer.msg("提交成功！",{
                time:2000
            },function () {
                    window.location.href="/";
            });
        }else{
            layer.msg(r.message);
        }
    });
}
