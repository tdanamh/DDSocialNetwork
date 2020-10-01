<%--
  Created by IntelliJ IDEA.
  User: Dan
  Date: 4/23/2019
  Time: 10:22 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Home</title>
    <link rel="stylesheet" href="style.css">
    <link rel="stylesheet" href="home_style.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.0/jquery.min.js"></script>
    <script>

        function addNewPost(){
            var postText = document.getElementById("newPost").value;
            document.getElementById("newPost").value = "";
            $.post("addNewPost",{newPost:postText}).done(function(){
                retrieveNewsFeed();
            });
        }

        $(document).ready(initialize);

        function initialize(){
            retrieveNewsFeed();
            getPeopleYouMayKnow();
            enableNotifications();
            getNotifications();
            enableSearch();
            enableFriends();
            getFriends();
            enableWatchingClick();
            enableLogOut();
            enableUserInfo();
        }

        function enableLogOut(){
            $("#logOutButton").on("click",function () {
                $.post("logOut",function (data) {
                    //redirect to index
                    window.location.replace("index.jsp");
                });
            });
        }

        function enableWatchingClick(){
            $(document).mouseup(function(e)
            {
                var friendsContainer = $("#friendsContainer");
                var notificationsContainer = $("#notificationsContainer");
                var searchResultContainer = $("#searchResultsContainer");

                if (!friendsContainer.is(e.target) && friendsContainer.has(e.target).length === 0) {
                    friendsContainer.hide();
                }

                if (!notificationsContainer.is(e.target) && notificationsContainer.has(e.target).length === 0) {
                    notificationsContainer.hide();
                }

                if (!searchResultContainer.is(e.target) && searchResultContainer.has(e.target).length === 0) {
                    searchResultContainer.hide();
                }
            });
        }

        function enableFriends(){
            $("#friendsOption").on('click', function(){
                $("#friendsContainer").css('display', 'block');
            });
        }

        function getFriends(){
            $.post("getFriends", function(data){
                $("#friends").empty();
                var dataArray = JSON.parse(data);
                console.log(dataArray);
                dataArray.forEach(function(object){
                $("#friends").append(
                    '<li class="friend" data-info=' + object["id"] + '>' +
                        '<div class="friend-info"><h4 class="friendName">' + object["name"] + '</h4></div>' +
                        '<div class="deleteFriend">' +
                            '<button class="deleteFriendButton">Remove friend</button>' +
                        '</div>' +
                    '</li>'
                );
                });
            }).done(function(){
                //delete friend
                $(".deleteFriendButton").on('click', function(){
                    // get id of friend that has to be deleted
                    var friendElement = $(this).parent().parent();
                    var friendId = $(friendElement).attr("data-info");
                   console.log(friendId);
                    //send to removeFriend class
                    // variable name : variable value
                    $.post("removeFriend",{friendId : friendId}).done(function(){   //after delete in DB, refresh friend list
                        getFriends();
                    });
                });
                //enter friend profile
                $(".friendName").on('click', getUserProfile);
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

        function getPeopleYouMayKnow(){
            $.post("getPeopleYouMayKnow", function(data){
               var users = JSON.parse(data);
               console.log(users);
               users.forEach(function(object){
                   $("#peopleList").append(
                       '<li class="people" data-info="' + object["id"] + '">' +
                           '<div class="peopleInfo">' +
                                '<img src="people.png" alt="">' +
                                '<h4 class="peopleName">' + object["name"] + '</h4>' +
                           '</div>' +
                           '<div class="addButtonContainer">' +
                                '<button class="addFriendButton">Add friend</button>' +
                           '</div>' +
                       '</li>'
                   );
               });
            }).done(function(){
                //add a friend
                $(".addFriendButton").on('click', function(){
                    var userContainer = $(this).parent().parent();
                    var userId = $(userContainer).attr('data-info');
                    $(userContainer).fadeOut("slow");
                    $.post("addFriend", {friendId:userId}).done(function(){
                        console.log("friend " + userId + " added");
                    });
                });
                //enter someone's profile
                $(".peopleName").on('click', getUserProfile);
            });
        }
        function enableUserInfo(){
            $("#yourProfile").on('click',function() {
                var friendId = $(this).attr("data-info");
                console.log(friendId);
                window.location = "profile.jsp?friendId=" + friendId;
            });
        }

        function getUserProfile(){
            //get id of friend
            var friendId = $(this).parent().parent().attr("data-info");
            console.log(friendId);

            window.location = "profile.jsp?friendId=" + friendId;   //set friendId
        }

        function retrieveNewsFeed(){
            $.post("retrieveNewsFeed", function(data){
                var newsFeed = JSON.parse(data);
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
    <div id="friendsContainer">
        <h4 class="friendsHeadingText">Friends</h4>
        <div id="friendsList">
            <ul id="friends">

            </ul>
        </div>

    </div>

    <div id="settingsPanel">

        <div class="settingsOption" id="yourProfile" data-info='<%=session.getAttribute("userId")%>' style="cursor: pointer; padding: 5px; width: 90%;" >
            <span class="settingsPanelText"><%= session.getAttribute("fullName") %></span>
        </div>

        <div class="profilePicture">
            <img src="<%=session.getAttribute("profilePicture")%>"><br>
            <form action="upload.php" method="POST" enctype="multipart/form-data">
                <span class="uploadText">Change your profile picture </span> <br>

                <div class="changeProfilePicture">
                    <label for="file-upload" class="custom-file-upload">Choose photo</label>
                    <input type="file" id ="file-upload">

                    <label for="file-submit" class="custom-file-submit">Submit</label>
                    <input type="submit" id ="file-submit">
                </div>

            </form>
        </div>

        <div id="friendsOption" class="settingsOption" style="cursor: pointer; padding: 5px; width: 90%; background: url(friends.png);
    background-repeat: no-repeat;">
            <span class="settingsPanelText" id ="friendLister">Friends</span>
        </div>

        <div id="peopleYouMayKnowContainer">
            <h4 class="youMayKnowText">People you may know...</h4>
            <div id="youMayKnow">
                <ul id="peopleList">

                </ul>
            </div>
        </div>
    </div>
    <div id="newsFeedPanel">
        <div class="addPost">
            <h4 class="newPostText">Create post</h4>
            <div class="textareaContainer">
                <textarea id="newPost" cols="30" rows="7" placeholder="What's on your mind, <%= session.getAttribute("fullName") %>?"></textarea>
            </div>
            <div class="postOption">
                <button class="postButton" onclick="addNewPost()">Post</button>
            </div>
        </div>
        <div class="newsFeed">
        </div>

    </div>
</div>

</body>
</html>
