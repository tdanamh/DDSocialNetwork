/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: Apache Tomcat/7.0.47
 * Generated at: 2019-06-07 21:21:09 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import org.json.JSONArray;

public final class profile_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final javax.servlet.jsp.JspFactory _jspxFactory =
          javax.servlet.jsp.JspFactory.getDefaultFactory();

  private static java.util.Map<java.lang.String,java.lang.Long> _jspx_dependants;

  private javax.el.ExpressionFactory _el_expressionfactory;
  private org.apache.tomcat.InstanceManager _jsp_instancemanager;

  public java.util.Map<java.lang.String,java.lang.Long> getDependants() {
    return _jspx_dependants;
  }

  public void _jspInit() {
    _el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
    _jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
  }

  public void _jspDestroy() {
  }

  public void _jspService(final javax.servlet.http.HttpServletRequest request, final javax.servlet.http.HttpServletResponse response)
        throws java.io.IOException, javax.servlet.ServletException {

    final javax.servlet.jsp.PageContext pageContext;
    javax.servlet.http.HttpSession session = null;
    final javax.servlet.ServletContext application;
    final javax.servlet.ServletConfig config;
    javax.servlet.jsp.JspWriter out = null;
    final java.lang.Object page = this;
    javax.servlet.jsp.JspWriter _jspx_out = null;
    javax.servlet.jsp.PageContext _jspx_page_context = null;


    try {
      response.setContentType("text/html;charset=UTF-8");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;

      out.write("\r\n");
      out.write("\r\n");
      out.write("<html>\r\n");
      out.write("<head>\r\n");
      out.write("    <title>Title</title>\r\n");
      out.write("    <link rel=\"stylesheet\" href=\"style.css\">\r\n");
      out.write("    <script src=\"https://ajax.googleapis.com/ajax/libs/jquery/3.4.0/jquery.min.js\"></script>\r\n");
      out.write("    <script>\r\n");
      out.write("        var friendId;\r\n");
      out.write("        $(document).ready(initialize);\r\n");
      out.write("\r\n");
      out.write("        function initialize(){\r\n");
      out.write("            friendId = ");
      out.print( request.getParameter("friendId"));
      out.write(";\r\n");
      out.write("\r\n");
      out.write("            retrieveFriendData();\r\n");
      out.write("            retrieveProfileFeed();\r\n");
      out.write("            enableNotifications();\r\n");
      out.write("            getNotifications();\r\n");
      out.write("            enableSearch();\r\n");
      out.write("            enableLogOut();\r\n");
      out.write("        }\r\n");
      out.write("\r\n");
      out.write("        function retrieveFriendData(){\r\n");
      out.write("            $.post(\"retrieveFriendData\",{friendId:friendId},function(data){\r\n");
      out.write("                var array = JSON.parse(data);\r\n");
      out.write("                $(\".username\").empty();\r\n");
      out.write("                $(\".profilePicture\").empty();\r\n");
      out.write("                $(\".registerDate\").empty();\r\n");
      out.write("                $(\".gender\").empty();\r\n");
      out.write("                $(\".noFriends\").empty();\r\n");
      out.write("\r\n");
      out.write("                $(\".username\").append(\"<span class='settingsText'>\" + array['name'] + \"</span>\");\r\n");
      out.write("                $(\".profilePicture\").append(\"<img src= \" + array['profilePicture'] + \">\");\r\n");
      out.write("                $(\".registerDate\").append(\"<span class='settingsText'>Since \" + array['registerDate'] + \"</span>\");\r\n");
      out.write("                $(\".gender\").append(\"<span class='settingsText'>Gender \" + array['gender'] + \"</span>\");\r\n");
      out.write("                $(\".noFriends\").append(\"<span class='settingsText'>Number of friends  \" + array['noFriends'] + \"</span>\");\r\n");
      out.write("\r\n");
      out.write("            });\r\n");
      out.write("        }\r\n");
      out.write("\r\n");
      out.write("        function enableLogOut(){\r\n");
      out.write("            $(\"#logOutButton\").on(\"click\",function () {\r\n");
      out.write("                $.post(\"logOut\",function (data) {\r\n");
      out.write("                    //redirect to index\r\n");
      out.write("                    window.location.replace(\"index.jsp\");\r\n");
      out.write("                });\r\n");
      out.write("            });\r\n");
      out.write("        }\r\n");
      out.write("\r\n");
      out.write("        function enableSearch(){\r\n");
      out.write("\r\n");
      out.write("            $(\"#searchField\").on('keyup', function(){\r\n");
      out.write("                $(\"#searchResultsContainer\").css('display','block');\r\n");
      out.write("                var searchedName = $(this).val();\r\n");
      out.write("\r\n");
      out.write("                $.post(\"searchByName\",{name:searchedName}, function(data){\r\n");
      out.write("                    var dataArray = JSON.parse(data);\r\n");
      out.write("                    console.log(dataArray);\r\n");
      out.write("                    $(\"#resultsList\").empty();\r\n");
      out.write("                    dataArray.forEach(function(object){\r\n");
      out.write("                        $(\"#resultsList\").append(\r\n");
      out.write("                            \"<li class='result' data-info=\" + object[\"id\"] + \"> <h4 class='friendName'>\" + object[\"name\"] + \"</h4></li>\"\r\n");
      out.write("                        );\r\n");
      out.write("                    });\r\n");
      out.write("                }).done(function(){\r\n");
      out.write("                    //enter friend profile\r\n");
      out.write("                    $(\".result\").on('click', function(){\r\n");
      out.write("                        //get id of friend\r\n");
      out.write("                        var friendId = $(this).attr(\"data-info\");\r\n");
      out.write("                        console.log(friendId);\r\n");
      out.write("\r\n");
      out.write("                        window.location = \"profile.jsp?friendId=\" + friendId;\r\n");
      out.write("                    });\r\n");
      out.write("                });\r\n");
      out.write("            });\r\n");
      out.write("        }\r\n");
      out.write("\r\n");
      out.write("        function enableNotifications(){\r\n");
      out.write("            $(\".topBarFeatures img\").on('click', function(){\r\n");
      out.write("                $(this).css('background-color','transparent');\r\n");
      out.write("                $(\"#notificationsContainer\").css(\"display\", \"block\");\r\n");
      out.write("            });\r\n");
      out.write("\r\n");
      out.write("        }\r\n");
      out.write("\r\n");
      out.write("        function getNotifications(){\r\n");
      out.write("            $.post(\"getNotifications\", function(data){\r\n");
      out.write("                $(\"#notificationsList\").empty();\r\n");
      out.write("                var dataArray = JSON.parse(data);\r\n");
      out.write("                if(dataArray.length > 0){\r\n");
      out.write("                    $(\".topBarFeatures img\").css('background-color', 'red');\r\n");
      out.write("                }\r\n");
      out.write("                dataArray.forEach(function(object){\r\n");
      out.write("                    $(\"#notificationsList\").append(\r\n");
      out.write("                        '<li class=\"notification\" data-info=' + object[\"id\"] + '>' +\r\n");
      out.write("                        '<h4 class=\"notificationText\">' + object[\"user\"] + ' sent you a friend request</h4>' +\r\n");
      out.write("                        '<button class=\"acceptButton\">Accept request</button>' +\r\n");
      out.write("                        '</li>'\r\n");
      out.write("                    );\r\n");
      out.write("                });\r\n");
      out.write("\r\n");
      out.write("            }).done(function(){\r\n");
      out.write("                $(\".acceptButton\").on('click', function(){\r\n");
      out.write("                    var notificationContainer = $(this).parent();\r\n");
      out.write("                    var notificationId = $(notificationContainer).attr('data-info');\r\n");
      out.write("                    console.log(notificationId);\r\n");
      out.write("                    $.post(\"acceptFriendRequest\", {requestId:notificationId}).done(function(){\r\n");
      out.write("                        getNotifications();\r\n");
      out.write("                        getFriends();\r\n");
      out.write("                        retrieveNewsFeed();\r\n");
      out.write("                    });\r\n");
      out.write("                });\r\n");
      out.write("            });\r\n");
      out.write("        }\r\n");
      out.write("\r\n");
      out.write("        function retrieveProfileFeed(){\r\n");
      out.write("            $.post(\"retrieveProfileFeed\",{friendId: friendId}, function(data){\r\n");
      out.write("                var newsFeed = JSON.parse(data);\r\n");
      out.write("                console.log(newsFeed);\r\n");
      out.write("                $(\".newsFeed\").empty();\r\n");
      out.write("\r\n");
      out.write("                newsFeed.forEach(function(object){\r\n");
      out.write("                    $(\".newsFeed\").prepend(\r\n");
      out.write("                        \"<div class='feed' data-info=\"+ object[\"postId\"] +\">\" +\r\n");
      out.write("\r\n");
      out.write("                        \"<h4 class='feedTextAuthor'>\" +\r\n");
      out.write("                        object[\"author\"] +\r\n");
      out.write("                        \"</h4>\" +\r\n");
      out.write("\r\n");
      out.write("                        \"<h4 class='feedTextTime'>\" +\r\n");
      out.write("                        object[\"publishDate\"] +\r\n");
      out.write("                        \"</h4>\" +\r\n");
      out.write("\r\n");
      out.write("                        \"<h4 class='feedText'>\" +\r\n");
      out.write("                        object[\"text\"] +\r\n");
      out.write("                        \"</h4>\" +\r\n");
      out.write("\r\n");
      out.write("                        \"<div class='postInfo'>\" +\r\n");
      out.write("                        \"<h4 class='feedLikes'>\" +\r\n");
      out.write("\r\n");
      out.write("                        \"</h4>\" +\r\n");
      out.write("\r\n");
      out.write("                        \"<h4 class='feedComments'>\" +\r\n");
      out.write("\r\n");
      out.write("                        \"</h4>\" +\r\n");
      out.write("                        \"</div>\" +\r\n");
      out.write("                        \"<div class='newsOption'>\" +\r\n");
      out.write("                        \"<button class='newsLikeButton'>\" +\r\n");
      out.write("                        \"Like\" +\r\n");
      out.write("                        \"</button>\" +\r\n");
      out.write("                        \"<button class='newsCommentButton'>\" +\r\n");
      out.write("                        \"Comment\" +\r\n");
      out.write("                        \"</button>\" +\r\n");
      out.write("                        \"</div>\" +\r\n");
      out.write("\r\n");
      out.write("                        \"<div class='comments'>\" +\r\n");
      out.write("                        \"<textarea id='newComment' cols='30' rows='1' placeholder='Write a comment...'></textarea>\" +\r\n");
      out.write("                        \"<div class='commentsList'>\" +\r\n");
      out.write("\r\n");
      out.write("                        \"</div>\" +\r\n");
      out.write("                        \"</div>\" +\r\n");
      out.write("                        \"</div>\"\r\n");
      out.write("                    )\r\n");
      out.write("                });\r\n");
      out.write("            }).done(function(){\r\n");
      out.write("                $(\".newsLikeButton\").on('click', function(){\r\n");
      out.write("                    var postContainer = this.parentElement.parentElement;\r\n");
      out.write("                    var postId = $(postContainer).attr(\"data-info\");\r\n");
      out.write("                    console.log(\"postID liked\" + postId);\r\n");
      out.write("                    $.post(\"LikePost\",{id:postId}).done(function(){\r\n");
      out.write("                        updatePostInfo();\r\n");
      out.write("                    });\r\n");
      out.write("                });\r\n");
      out.write("                updatePostInfo();\r\n");
      out.write("\r\n");
      out.write("                $(\".newsCommentButton\").on('click', function(){\r\n");
      out.write("                    var postContainer = this.parentElement.parentElement;\r\n");
      out.write("                    var postId = $(postContainer).attr(\"data-info\");\r\n");
      out.write("\r\n");
      out.write("                    getComments(postContainer, postId);\r\n");
      out.write("\r\n");
      out.write("                    var commentsSection = $(postContainer).children()[5];\r\n");
      out.write("                    var currentVisibility = $(commentsSection).css(\"display\");\r\n");
      out.write("                    var currentTextArea = $(commentsSection).children()[0];\r\n");
      out.write("\r\n");
      out.write("                    if(currentVisibility === \"none\"){\r\n");
      out.write("                        $(commentsSection).css(\"display\", \"block\");\r\n");
      out.write("                        $(currentTextArea).focus();\r\n");
      out.write("                    }\r\n");
      out.write("                    else {\r\n");
      out.write("                        $(currentTextArea).focus();\r\n");
      out.write("                    }\r\n");
      out.write("\r\n");
      out.write("                    $(currentTextArea).on(\"keypress\", function(e){\r\n");
      out.write("                        if(e.keyCode == 13 && !e.shiftKey){\r\n");
      out.write("                            e.preventDefault();\r\n");
      out.write("                            var commentText = this.value;\r\n");
      out.write("                            console.log(commentText + \" \" + postId);\r\n");
      out.write("                            $.post(\"writeComment\",{id:postId, text:commentText}).done(function(){\r\n");
      out.write("                                getComments(postContainer, postId);\r\n");
      out.write("                                updatePostInfo();\r\n");
      out.write("                                $(currentTextArea).val(\"\");\r\n");
      out.write("                            });\r\n");
      out.write("                        }\r\n");
      out.write("                    });\r\n");
      out.write("                });\r\n");
      out.write("\r\n");
      out.write("            });\r\n");
      out.write("        }\r\n");
      out.write("\r\n");
      out.write("        function getComments(postContainer, postId){\r\n");
      out.write("            $.post(\"retrieveComments\", {id: postId}, function(data){\r\n");
      out.write("                var commentsArray = JSON.parse(data);\r\n");
      out.write("                console.log(commentsArray);\r\n");
      out.write("                var commentsSection = $(postContainer).children()[5];\r\n");
      out.write("                var commentsList = $(commentsSection).children()[1];\r\n");
      out.write("                $(commentsList).empty();\r\n");
      out.write("                commentsArray.forEach(function(object){\r\n");
      out.write("                    $(commentsList).prepend(\r\n");
      out.write("                        \"<div class='eachComment'>\" +\r\n");
      out.write("                        \"<div class='commentInfo'>\" +\r\n");
      out.write("                        \"<h4 class='commentAuthor'>\" +\r\n");
      out.write("                        object['author'] +\r\n");
      out.write("                        \"</h4>\" +\r\n");
      out.write("\r\n");
      out.write("                        \"<h4 class='commentTime'>\" +\r\n");
      out.write("                        object['publishDate'] +\r\n");
      out.write("                        \"</h4>\" +\r\n");
      out.write("\r\n");
      out.write("                        \"</div>\" +\r\n");
      out.write("                        \"<h4 class='commentText'>\" +\r\n");
      out.write("                        object['text'] +\r\n");
      out.write("                        \"</h4>\" +\r\n");
      out.write("                        \"</div>\");\r\n");
      out.write("                });\r\n");
      out.write("            });\r\n");
      out.write("        }\r\n");
      out.write("\r\n");
      out.write("        function updatePostInfo(){\r\n");
      out.write("            $.post(\"UpdatePostInfo\", function(data){\r\n");
      out.write("                var likesArray = JSON.parse(data);\r\n");
      out.write("                console.log(likesArray);\r\n");
      out.write("                likesArray.forEach(function(object){\r\n");
      out.write("\r\n");
      out.write("                    var postToUpdate = $(\".feed[data-info='\" + object[\"postId\"] + \"']\");\r\n");
      out.write("                    var likesCommentsContainer = $(postToUpdate).children()[3];\r\n");
      out.write("                    var likesContainer = $(likesCommentsContainer).children()[0];\r\n");
      out.write("                    var commentsContainer = $(likesCommentsContainer).children()[1];\r\n");
      out.write("                    $(likesContainer).text(object[\"count\"] + \" likes\");\r\n");
      out.write("                    $(commentsContainer).text(object[\"comments\"] + \" comments\");\r\n");
      out.write("\r\n");
      out.write("                });\r\n");
      out.write("\r\n");
      out.write("            });\r\n");
      out.write("        }\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("    </script>\r\n");
      out.write("\r\n");
      out.write("</head>\r\n");
      out.write("<body>\r\n");
      out.write("\r\n");
      out.write("<div class=\"topBar\">\r\n");
      out.write("    <div class=\"headingTextContainer\" onclick=\"window.location='home.jsp'\">\r\n");
      out.write("        <h2 class=\"headingText\">DD Social Network</h2>\r\n");
      out.write("    </div>\r\n");
      out.write("    <div class=\"searchContainer\">\r\n");
      out.write("        <input id=\"searchField\" type=\"text\" placeholder=\"Search\">\r\n");
      out.write("    </div>\r\n");
      out.write("    <div class=\"topBarFeatures\">\r\n");
      out.write("        <img src=\"bell.png\" alt=\"\" width=\"30\">\r\n");
      out.write("    </div>\r\n");
      out.write("    <div class=\"logOutContainer\">\r\n");
      out.write("        <button id=\"logOutButton\"> Log out</button>\r\n");
      out.write("    </div>\r\n");
      out.write("</div>\r\n");
      out.write("\r\n");
      out.write("<div id=\"mainView\">\r\n");
      out.write("    <div id=\"notificationsContainer\">\r\n");
      out.write("        <div id=\"notificationsHeading\">\r\n");
      out.write("            <h4 class=\"notificationsHeadingText\">Notifications</h4>\r\n");
      out.write("        </div>\r\n");
      out.write("        <div id=\"notifications\">\r\n");
      out.write("            <ul id=\"notificationsList\">\r\n");
      out.write("            </ul>\r\n");
      out.write("        </div>\r\n");
      out.write("    </div>\r\n");
      out.write("    <div id=\"searchResultsContainer\">\r\n");
      out.write("        <div id=\"searchResults\">\r\n");
      out.write("            <ul id=\"resultsList\">\r\n");
      out.write("\r\n");
      out.write("            </ul>\r\n");
      out.write("        </div>\r\n");
      out.write("    </div>\r\n");
      out.write("\r\n");
      out.write("    <div id=\"settingsPanel\">\r\n");
      out.write("       <div class=\"username\">\r\n");
      out.write("\r\n");
      out.write("       </div>\r\n");
      out.write("\r\n");
      out.write("        <div class=\"profilePicture\">\r\n");
      out.write("\r\n");
      out.write("        </div>\r\n");
      out.write("        <div class=\"registerDate\">\r\n");
      out.write("\r\n");
      out.write("        </div>\r\n");
      out.write("        <div class=\"gender\">\r\n");
      out.write("\r\n");
      out.write("        </div>\r\n");
      out.write("        <div class=\"noFriends\">\r\n");
      out.write("\r\n");
      out.write("        </div>\r\n");
      out.write("\r\n");
      out.write("    </div>\r\n");
      out.write("\r\n");
      out.write("    <div id=\"newsFeedPanel\">\r\n");
      out.write("\r\n");
      out.write("        <div class=\"newsFeed\">\r\n");
      out.write("        </div>\r\n");
      out.write("\r\n");
      out.write("    </div>\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("</div>\r\n");
      out.write("\r\n");
      out.write("</body>\r\n");
      out.write("</html>\r\n");
    } catch (java.lang.Throwable t) {
      if (!(t instanceof javax.servlet.jsp.SkipPageException)){
        out = _jspx_out;
        if (out != null && out.getBufferSize() != 0)
          try { out.clearBuffer(); } catch (java.io.IOException e) {}
        if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
        else throw new ServletException(t);
      }
    } finally {
      _jspxFactory.releasePageContext(_jspx_page_context);
    }
  }
}
