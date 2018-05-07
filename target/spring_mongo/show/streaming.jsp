<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html lang="en">
<head>
    <meta charset="utf-8" />
    <link rel="icon" type="image/png" href="assets/img/favicon.ico">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />

    <title></title>

    <meta content='width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0' name='viewport' />
    <meta name="viewport" content="width=device-width" />

    <!-- Bootstrap core CSS     -->
    <link href="assets/css/bootstrap.min.css" rel="stylesheet" />

    <!-- Animation library for notifications   -->
    <link href="assets/css/animate.min.css" rel="stylesheet"/>

    <!--  Light Bootstrap Table core CSS    -->
    <link href="assets/css/light-bootstrap-dashboard.css" rel="stylesheet"/>


    <!--  CSS for Demo Purpose, don't include it in your project     -->
    <link href="assets/css/demo.css" rel="stylesheet" />

    <!--     Fonts and icons     -->
    <link href="assets/css/pe-icon-7-stroke.css" rel="stylesheet" />

    <style type="text/css">
        #container{height:100%}
    </style>

    <script src="js/jquery-3.1.1.min.js"></script>
    <script src="js/highcharts.js"></script>
    <script src="js/exporting.js"></script>
</head>
<body>

<div class="wrapper">
    <div class="sidebar" data-color="purple" data-image="assets/img/sidebar-5.jpg">
        <div class="sidebar-wrapper">
            <div class="logo">
                <a href="index.html" class="simple-text">
                    Index Page
                </a>
            </div>

            <ul class="nav">
                <li>
                    <a href="dashboard.html">
                        <i class="pe-7s-graph"></i>
                        <p>Dashboard</p>
                    </a>
                </li>
                <li>
                    <a href="table.jsp">
                        <i class="pe-7s-note2"></i>
                        <p>Table List</p>
                    </a>
                </li>
                <li>
                    <a href="maps.html">
                        <i class="pe-7s-map-marker"></i>
                        <p>Maps</p>
                    </a>
                </li>
                <li class="active">
                    <a href="streaming.jsp">
                        <i class="pe-7s-science"></i>
                        <p>streaming</p>
                    </a>
                </li>
            </ul>
        </div>
    </div>

    <div class="main-panel">
        <nav class="navbar navbar-default navbar-fixed">
            <div class="container-fluid">
                <div class="navbar-header">
                    <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#navigation-example-2">
                        <span class="sr-only">Toggle navigation</span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                    </button>
                    <a class="navbar-brand" href="#">Streaming</a>
                </div>
                <div class="collapse navbar-collapse">
                    <ul class="nav navbar-nav navbar-right">
                        <li>
                            <a href="">
                                Account
                            </a>
                        </li>
                        <li class="dropdown">
                            <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                                Dropdown
                                <b class="caret"></b>
                            </a>
                            <ul class="dropdown-menu">
                                <li><a href="#">Action</a></li>
                                <li><a href="#">Another action</a></li>
                                <li><a href="#">Something</a></li>
                                <li><a href="#">Another action</a></li>
                                <li><a href="#">Something</a></li>
                                <li class="divider"></li>
                                <li><a href="#">Separated link</a></li>
                            </ul>
                        </li>
                        <li>
                            <a href="#">
                                Log out
                            </a>
                        </li>
                    </ul>
                </div>
            </div>
        </nav>

        <div class="content">
            <div class="container-fluid">
                <div class="row">
                    <div class="col-md-12">
                        <div class="card">
                            <div class="header">
                                <h4 class="title">实时计算男女购买人数</h4>
                            </div>
                            <div class="content">

                                <div>
                                    <b>Girl: </b><b id="girl"></b>
                                    <b>Boy: </b><b id="boy"></b>
                                </div>
                                <div id="container" style="width: 50%;height:auto;"></div>

                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <footer class="footer">
                <div class="container-fluid">
                    <nav class="pull-left">
                        <ul>
                            <li>
                                <a href="#">
                                    Home
                                </a>
                            </li>
                            <li>
                                <a href="#">
                                    Company
                                </a>
                            </li>
                            <li>
                                <a href="#">
                                    Portfolio
                                </a>
                            </li>
                            <li>
                                <a href="#">
                                    Blog
                                </a>
                            </li>
                        </ul>
                    </nav>
                    <p class="copyright pull-right">
                        Copyright &copy; 2017.Company name All rights reserved.<a target="_blank" href="http://sc.chinaz.com/moban/">&#x7F51;&#x9875;&#x6A21;&#x677F;</a>
                    </p>
                </div>
            </footer>
        </div>
    </div>
</div>


</body>

<!--   Core JS Files   -->
<script src="assets/js/jquery-1.10.2.js" type="text/javascript"></script>
<script src="assets/js/bootstrap.min.js" type="text/javascript"></script>

<!--  Checkbox, Radio & Switch Plugins -->
<script src="assets/js/bootstrap-checkbox-radio-switch.js"></script>

<!--  Charts Plugin -->
<script src="assets/js/chartist.min.js"></script>

<!--  Notifications Plugin    -->
<script src="assets/js/bootstrap-notify.js"></script>

<!-- Light Bootstrap Table Core javascript and methods for Demo purpose -->
<script src="assets/js/light-bootstrap-dashboard.js"></script>

<script type="text/javascript">
    var websocket = null;
    //判断当前浏览器是否支持WebSocket
    if ('WebSocket' in window) {
        websocket = new WebSocket("ws://localhost:8080/ssmm/ws/count");

        //连接成功建立的回调方法
        websocket.onopen = function () {
            websocket.send("客户端链接成功");
        }

        //接收到消息的回调方法
        websocket.onmessage = function (event) {
            setMessageInnerHTML(event.data);
        }

        //连接发生错误的回调方法
        websocket.onerror = function () {
            alert("WebSocket连接发生错误");
        };

        //连接关闭的回调方法
        websocket.onclose = function () {
//            alert("WebSocket连接关闭");
        }

        //监听窗口关闭事件，当窗口关闭时，主动去关闭websocket连接，防止连接还没断开就关闭窗口，server端会抛异常。
        window.onbeforeunload = function () {
            closeWebSocket();
        }

    }
    else {
        alert('当前浏览器 Not support websocket')
    }

    //将消息显示在网页上
    function setMessageInnerHTML(innerHTML) {
        var obj = eval("("+innerHTML+")");
        var result = obj["data"].split(",");
        $('#girl').html(result[0]);
        $('#boy').html(result[1]);
    }

    //关闭WebSocket连接
    function closeWebSocket() {
        websocket.close();
    }
</script>
<script type="text/javascript">
    $(document).ready(function () {
        Highcharts.setOptions({
            global: {
                useUTC: false
            }
        });

        Highcharts.chart('container', {
            chart: {
                type: 'spline',
                animation: Highcharts.svg, // don't animate in old IE
                marginRight: 10,
                events: {
                    load: function () {

                        // set up the updating of the chart each second
                        var series1 = this.series[0];
                        var series2 = this.series[1];
                        setInterval(function () {
                            var x = (new Date()).getTime(); // current time
                            var count1 = $('#girl').text();
                            y = parseInt(count1);
                            series1.addPoint([x, y], true, true);

                            count2 = $('#boy').text();
                            z = parseInt(count2);
                            series2.addPoint([x, z], true, true);
                        }, 1000);
                    }
                }
            },
            title: {
                text: '男女生购物人数实时分析'
            },
            xAxis: {
                type: 'datetime',
                tickPixelInterval: 50
            },
            yAxis: {
                title: {
                    text: '数量'
                },
                plotLines: [{
                    value: 0,
                    width: 1,
                    color: '#808080'
                }]
            },
            tooltip: {
                formatter: function () {
                    return '<b>' + this.series.name + '</b><br/>' +
                        Highcharts.dateFormat('%Y-%m-%d %H:%M:%S', this.x) + '<br/>' +
                        Highcharts.numberFormat(this.y, 2);
                }
            },
            legend: {
                enabled: true
            },
            exporting: {
                enabled: true
            },
            series: [{
                name: '女生购物人数',
                data: (function () {
                    // generate an array of random data
                    var data = [],
                        time = (new Date()).getTime(),
                        i;

                    for (i = -10; i <= 0; i += 1) {
                        data.push({
                            x: time + i * 1000,
                            y: Math.random()*10
//                            y: 0
                        });
                    }
                    return data;
                }())
            },
                {
                    name: '男生购物人数',
                    data: (function () {
                        // generate an array of random data
                        var data = [],
                            time = (new Date()).getTime(),
                            i;

                        for (i = -10; i <= 0; i += 1) {
                            data.push({
                                x: time + i * 1000,
                                y: Math.random()*10
//                                y: 0
                            });
                        }
                        return data;
                    }())
                }]
        });
    });
</script>
</html>

