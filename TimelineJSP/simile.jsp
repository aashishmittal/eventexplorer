<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
    <%@ page import="org.json.JSONObject" %>
	<%@ page import="org.json.JSONArray" %>
	<%@ page import="cass.User" %>	
	<%@ page import="cass.Base" %>	
    
    
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
   <!-- See http://developer.yahoo.com/yui/grids/ for info on the grid layout -->
   <title>Sakai Event Explorer</title>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

   <!-- See http://developer.yahoo.com/yui/ for info on the reset, font and base css -->
   <link rel="stylesheet" href="http://yui.yahooapis.com/2.7.0/build/reset-fonts-grids/reset-fonts-grids.css" type="text/css">
   <link rel="stylesheet" type="text/css" href="http://yui.yahooapis.com/2.7.0/build/base/base-min.css"> 

   <!-- Load the Timeline library after reseting the fonts, etc -->
   <script src="http://static.simile.mit.edu/timeline/api-2.3.0/timeline-api.js?bundle=true" type="text/javascript"></script>
 
   <link rel="stylesheet" href="local_example.css" type="text/css">

  
<%! JSONObject j;%> 

<%

User u=new User();
j=u.jUser;

%>
   
   <script>        
        var tl;
		
 		
        function onLoad() {
            var tl_el = document.getElementById("tl");
            var eventSource1 = new Timeline.DefaultEventSource();
            
			var test=<%=j%>
			
            var theme1 = Timeline.ClassicTheme.create();
            theme1.autoWidth = true; // Set the Timeline's "width" automatically.
                                     // Set autoWidth on the Timeline's first band's theme,
                                     // will affect all bands.
            theme1.timeline_start = new Date(Date.UTC(1890, 0, 1));
            theme1.timeline_stop  = new Date(Date.UTC(2050, 0, 1));
                  
            var d = Timeline.DateTime.parseGregorianDateTime("1900")
            var bandInfos = [
                Timeline.createBandInfo({
                    width:          45, // set to a minimum, autoWidth will then adjust
                    intervalUnit:   Timeline.DateTime.DECADE, 
                    intervalPixels: 200,
                    eventSource:    eventSource1,
                    date:           d,
                    theme:          theme1,
                    layout:         'original'  // original, overview, detailed
                })
            ];
                                                            
            // create the Timeline
            tl = Timeline.create(tl_el, bandInfos, Timeline.HORIZONTAL);
            
            var url = '.'; // The base url for image, icon and background image
                           // references in the data
            eventSource1.loadJSON(test , url); // loading the json object
            tl.layout(); // display the Timeline
        }
        
        var resizeTimerID = null;
        function onResize() {
            if (resizeTimerID == null) {
                resizeTimerID = window.setTimeout(function() {
                    resizeTimerID = null;
                    tl.layout();
                }, 500);
            }
        }
   </script>

</head>
<body onload="onLoad();" onresize="onResize();">
<div id="doc3" class="yui-t7">
   <div id="hd" role="banner">
     <h1>Sakai Event Explorer View page</h1>
     <p>This page calls the User class which retrieves data from Cassandra, creates JSON objects of the data and feeds
	  it to the Simile JSP for display.</p>
     <p>The Timeline will grow automatically to fit the events. </p>
   </div>
   <div id="bd" role="main">
	   <div class="yui-g">
	     <div id='tl'></div>
	     <p>To move the Timeline: use the mouse scroll wheel, the arrow keys or grab and drag the Timeline.</p>

		 </div>
	 </div>
</div>

</body>
</html>