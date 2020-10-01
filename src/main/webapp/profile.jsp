<%@ page import="org.json.JSONArray" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" href="style.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.0/jquery.min.js"></script>
    <script>
        var friendId;
        $(document).ready(initialize);

        function initialize(){
            friendId = <%= request.getParameter("friendId")%>;

            retrieveFriendData();
            retrieveProfileFeed();
            enableNotifications();
            getNotifications();
            enableSearch();
            enableLogOut();
        }

        function retrieveFriendData(){
            $.post("retrieveFriendData",{friendId:friendId},function(data){
                var array = JSON.parse(data);
                $(".username").empty();
                $(".profilePicture").empty();
                $(".registerDate").empty();
                $(".gender").empty();
                $(".noFriends").empty();

                $(".username").append("<span class='settingsText'>" + array['name'] + "</span>");
                $(".profilePicture").append("<img src= " + array['profilePicture'] + ">");
                $(".registerDate").append("<span class='settingsText'>Since " + array['registerDate'] + "</span>");
                $(".gender").append("<span class='settingsText'>Gender " + array['gender'] + "</span>");
                $(".noFriends").append("<span class='settingsText'>Number of friends  " + array['noFriends'] + "</span>");

            });
        }

        function enableLogOut(){
            $("#logOutButton").on("click",function () {
                $.post("logOut",function (data) {
                    //redirect to index
                    window.location.replace("index.jsp");
                });
            });
        }

        function enableSearch(){

            $("#searchField").on('keyup', function(){
                $("#searchResultsContainer").css('display','block');
                var searchedName = $(this).val();

                $.post("searchByName",{name:searchedName}, function(data){
                    var dataArray = JSON.parse(data);
                    console.log(dataArray);
                    $("#resultsList").empty();
                    dataArray.forEach(function(object){
                        $("#resultsList").append(
                            "<li class='result' data-info=" + object["id"] + "> <h4 class='friendName'>" + object["name"] + "</h4></li>"
                        );
                    });
                }).done(function(){
                    //enter friend profile
                    $(".result").on('click', function(){
                        //get id of friend
                        var friendId = $(this).attr("data-info");
                        console.log(friendId);

                        window.location = "profile.jsp?friendId=" + friendId;
                    });
                });
            });
        }

        function enableNotifications(){
            $(".topBarFeatures img").on('click', function(){
                $(this).css('background-color','transparent');
                $("#notificationsContainer").css("display", "block");
            });

        }

        function getNotifications(){
            $.post("getNotifications", function(data){
                $("#notificationsList").empty();
                var dataArray = JSON.parse(data);
                if(dataArray.length > 0){
                    $(".topBarFeatures img").css('background-color', 'red');
                }
                dataArray.forEach(function(object){
                    $("#notificationsList").append(
                        '<li class="notification" data-info=' + object["id"] + '>' +
                        '<h4 class="notificationText">' + object["user"] + ' sent you a friend request</h4>' +
                        '<button class="acceptButton">Accept request</button>' +
                        '</li>'
                    );
                });

            }).done(function(){
                $(".acceptButton").on('click', function(){
                    var notificationContainer = $(this).parent();
                    var notificationId = $(notificationContainer).attr('data-info');
                    console.log(notificationId);
                    $.post("acceptFriendRequest", {requestId:notificationId}).done(function(){
                        getNotifications();
                        getFriends();
                        retrieveNewsFeed();
                    });
                });
            });
        }

        function retrieveProfileFeed(){
            $.post("retrieveProfileFeed",{friendId: friendId}, function(data){
                var newsFeed = JSON.parse(data);
                console.log(newsFeed);
                $(".newsFeed").empty();

                newsFeed.forEach(function(object){
                    $(".newsFeed").prepend(
                        "<div class='feed' data-info="+ object["postId"] +">" +

                        "<h4 class='feedTextAuthor'>" +
                        object["author"] +
                        "</h4>" +

                        "<h4 class='feedTextTime'>" +
                        object["publishDate"] +
                        "</h4>" +

                        "<h4 class='feedText'>" +
                        object["text"] +
                        "</h4>" +

                        "<div class='postInfo'>" +
                        "<h4 class='feedLikes'>" +

                        "</h4>" +

                        "<h4 class='feedComments'>" +

                        "</h4>" +
                        "</div>" +
                        "<div class='newsOption'>" +
                        "<button class='newsLikeButton'>" +
                        "Like" +
                        "</button>" +
                        "<button class='newsCommentButton'>" +
                        "Comment" +
                        "</button>" +
                        "</div>" +

                        "<div class='comments'>" +
                        "<textarea id='newComment' cols='30' rows='1' placeholder='Write a comment...'></textarea>" +
                        "<div class='commentsList'>" +

                        "</div>" +
                        "</div>" +
                        "</div>"
                    )
                });
            }).done(function(){
                $(".newsLikeButton").on('click', function(){
                    var postContainer = this.parentElement.parentElement;
                    var postId = $(postContainer).attr("data-info");
                    console.log("postID liked" + postId);
                    $.post("LikePost",{id:postId}).done(function(){
                        updatePostInfo();
                    });
                });
                updatePostInfo();

                $(".newsCommentButton").on('click', function(){
                    var postContainer = this.parentElement.parentElement;
                    var postId = $(postContainer).attr("data-info");

                    getComments(postContainer, postId);

                    var commentsSection = $(postContainer).children()[5];
                    var currentVisibility = $(commentsSection).css("display");
                    var currentTextArea = $(commentsSection).children()[0];

                    if(currentVisibility === "none"){
                        $(commentsSection).css("display", "block");
                        $(currentTextArea).focus();
                    }
                    else {
                        $(currentTextArea).focus();
                    }

                    $(currentTextArea).on("keypress", function(e){
                        if(e.keyCode == 13 && !e.shiftKey){
                            e.preventDefault();
                            var commentText = this.value;
                            console.log(commentText + " " + postId);
                            $.post("writeComment",{id:postId, text:commentText}).done(function(){
                                getComments(postContainer, postId);
                                updatePostInfo();
                                $(currentTextArea).val("");
                            });
                        }
                    });
                });

            });
        }

        function getComments(postContainer, postId){
            $.post("retrieveComments", {id: postId}, function(data){
                var commentsArray = JSON.parse(data);
                console.log(commentsArray);
                var commentsSection = $(postContainer).children()[5];
                var commentsList = $(commentsSection).children()[1];
                $(commentsList).empty();
                commentsArray.forEach(function(object){
                    $(commentsList).prepend(
                        "<div class='eachComment'>" +
                        "<div class='commentInfo'>" +
                        "<h4 class='commentAuthor'>" +
                        object['author'] +
                        "</h4>" +

                        "<h4 class='commentTime'>" +
                        object['publishDate'] +
                        "</h4>" +

                        "</div>" +
                        "<h4 class='commentText'>" +
                        object['text'] +
                        "</h4>" +
                        "</div>");
                });
            });
        }

        function updatePostInfo(){
            $.post("UpdatePostInfo", function(data){
                var likesArray = JSON.parse(data);
                console.log(likesArray);
                likesArray.forEach(function(object){

                    var postToUpdate = $(".feed[data-info='" + object["postId"] + "']");
                    var likesCommentsContainer = $(postToUpdate).children()[3];
                    var likesContainer = $(likesCommentsContainer).children()[0];
                    var commentsContainer = $(likesCommentsContainer).children()[1];
                    $(likesContainer).text(object["count"] + " likes");
                    $(commentsContainer).text(object["comments"] + " comments");

                });

            });
        }


    </script>

</head>
<body>

<div class="topBar">
    <div class="headingTextContainer" onclick="window.location='home.jsp'">
        <h2 class="headingText">DD Social Network</h2>
    </div>
    <div class="searchContainer">
        <input id="searchField" type="text" placeholder="Search">
    </div>
    <div class="topBarFeatures">
        <img src="bell.png" alt="" width="30">
    </div>
    <div class="logOutContainer">
        <button id="logOutButton"> Log out</button>
    </div>
</div>

<div id="mainView">
    <div id="notificationsContainer">
        <div id="notificationsHeading">
            <h4 class="notificationsHeadingText">Notifications</h4>
        </div>
        <div id="notifications">
            <ul id="notificationsList">
            </ul>
        </div>
    </div>
    <div id="searchResultsContainer">
        <div id="searchResults">
            <ul id="resultsList">

            </ul>
        </div>
    </div>

    <div id="settingsPanel">
       <div class="username">

       </div>

        <div class="profilePicture">

        </div>
        <div class="registerDate">

        </div>
        <div class="gender">

        </div>
        <div class="noFriends">

        </div>

    </div>

    <div id="newsFeedPanel">

        <div class="newsFeed">
        </div>

    </div>


</div>

</body>
</html>
