<%-- 
    Document   : fitnessChart
    Created on : 28 Mar 2020, 22:14:26
    Author     : cooke
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">

<!--   D3 is always loaded before dimple as dimple is built on top of D3/references D3 funcctions
  here we are referencing the javascript files that are available on the web
  if you do not have web access you will have to reference the files in your project sfile structure as we did in the first tutorial -->
  <script src="http://d3js.org/d3.v3.min.js"></script>
  <script src="http://dimplejs.org/dist/dimple.v2.0.0.min.js"></script>
    <script type="text/javascript">
      function draw(data) {
          "use strict";
         
          var margin = 75,
              width = 1400 - margin,
              height = 600 - margin;

debugger;
          var svg = d3.select("body")
            .append("svg")
              .attr("width", width + margin)
              .attr("height", height + margin)
            .append('g')
                .attr('class','chart');

      
          var myChart = new dimple.chart(svg, data);
          var x = myChart.addTimeAxis("x", "date");
          x.dateParseFormat = "%Y";
          debugger;
          var y = myChart.addMeasureAxis("y", "time");
          var series = myChart.addSeries("name", dimple.plot.line);
          var series = myChart.addSeries("name", dimple.plot.scatter);
          myChart.draw();

        };
      </script>
  </head>
<body>
  <script type="text/javascript">
  
  d3.csv("TestResults.csv", draw);
  </script>
</body>
</html>