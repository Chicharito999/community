function post() {
    var questionId = $("#question_id").val();
    var content = $("#comment_content").val();
    if (!content){
        alert("不能回复空内容！");
        return;
    }
    $.ajax({
        type:"POST",
        url:"/comment",
        contentType:'application/json',
        data:JSON.stringify({
            "parentId":questionId,
            "content":content,
            "type":1
        }),
        success:function (response) {
            if (response.code==200){
                //$("#comment_section").hide();
                window.location.reload();
            }else{
                if (response.code==202){
                    var isAccpted = confirm(response.message);
                    if (isAccpted){
                        window.open("https://github.com/login/oauth/authorize?client_id=5d1932cfd241fa12a215&redirect_uri=http://localhost:8887/callback&scope=user&state=1");
                        window.localStorage.setItem("closable",true);
                    }
                }
                alert(response.message);
            }
            console.log(response);
        },
        dataType:"json"
    });
}