<%-- 
    Document   : fitnessChart
    Created on : 28 Mar 2020, 22:14:26
    Author     : cooke
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
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
                x.dateParseFormat = "%m-%Y";
                
                debugger;
                
                var y = myChart.addMeasureAxis("y", "time");
                var series = myChart.addSeries("name", dimple.plot.line);
                var series = myChart.addSeries("name", dimple.plot.scatter);
                var myLegend = myChart.addLegend(1350, 100, 60, 300, "Right");
                myChart.ease = "linear";
                myChart.draw(1000);
                myChart.legends = [];
                var filterValues = dimple.getUniqueValues(data, "postion");

                myLegend.shapes.selectAll("rect")
                // Add a click event to each rectangle
                    .on("click", function (e) {
                // This indicates whether the item is already visible or not
                var hide = false;
                var newFilters = [];
                // If the filters contain the clicked shape hide it
                filterValues.forEach(function (f) {
                    if (f === e.aggField.slice(-1)[0]) {
                        hide = true;
                    } else {
                    newFilters.push(f);
                    }
                });
                
                // Hide the shape or show it
                
                if (hide) {
                    d3.select(this).style("opacity", 0.2);
                } else {
                    newFilters.push(e.aggField.slice(-1)[0]);
                    d3.select(this).style("opacity", 0.8);
                }
                
                // Update the filters
                filterValues = newFilters;
                
                // Filter the data
                myChart.data = dimple.filterData(data, "postion", filterValues);
                
                // Passing a duration parameter makes the chart animate. Without
                // it there is no transition
                myChart.draw(800);
                })
            };
        </script>
    <sec:authorize access="hasRole('COACH')">
        <%@include file="navBarCoach.jsp"%>
    </sec:authorize>

    <sec:authorize access="hasRole('PLAYER')">
        <%@include file="navBarPlayer.jsp"%>
    </sec:authorize>


    <script type="text/javascript">

        d3.json(${testResults}, draw);
    </script>
</body>
</html>