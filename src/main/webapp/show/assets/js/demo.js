type = ['','info','success','warning','danger'];
var dataMap = {};
function maxLoad(obj, loadname) {
    var max = 0;
    var sum = 0;
    for (var i = 0; i < obj.length; i++){
        var load = obj[i].split(',')[1];
        max = Math.max(max, load);
        sum += Number(load);
    }
    dataMap[loadname + 'max'] = max;
    dataMap[loadname + 'sum'] = sum;
}

demo = {
    initPickColor: function(){
        $('.pick-class-label').click(function(){
            var new_class = $(this).attr('new-class');
            var old_class = $('#display-buttons').attr('data-class');
            var display_div = $('#display-buttons');
            if(display_div.length) {
            var display_buttons = display_div.find('.btn');
            display_buttons.removeClass(old_class);
            display_buttons.addClass(new_class);
            display_div.attr('data-class', new_class);
            }
        });
    },
    initPath : function () {
        var svg = d3.select("#svg3"),
            margin = {top: 20, right: 80, bottom: 30, left: 50},
            width = svg.attr("width") - margin.left - margin.right,
            height = svg.attr("height") - margin.top - margin.bottom,
            g = svg.append("g").attr("transform", "translate(" + margin.left + "," + margin.top + ")");

        var parseTime = d3.timeParse("%Y%m%d");

        var x = d3.scaleTime().range([0, width]),
            y = d3.scaleLinear().range([height, 0]),
            z = d3.scaleOrdinal(d3.schemeCategory10);

        var line = d3.line()
            .curve(d3.curveBasis)
            .x(function(d) { return x(d.date); })
            .y(function(d) { return y(d.temperature); });

        d3.tsv("../csv/temp.tsv", type, function(error, data) {
            if (error) throw error;

            var cities = data.columns.slice(1).map(function(id) {
                return {
                    id: id,         //这个id值为三个城市的名字
                    values: data.map(function(d) {
                        return {date: d.date, temperature: d[id]};
                    })
                };
            });
            x.domain(d3.extent(data, function(d) { return d.date; }));

            y.domain([
                d3.min(cities, function(c) { return d3.min(c.values, function(d) { return d.temperature; }); }),
                d3.max(cities, function(c) { return d3.max(c.values, function(d) { return d.temperature; }); })
            ]);

            z.domain(cities.map(function(c) { return c.id; }));

            g.append("g")
                .attr("class", "axis axis--x")
                .attr("transform", "translate(0," + height + ")")
                .call(d3.axisBottom(x));

            g.append("g")
                .attr("class", "axis axis--y")
                .call(d3.axisLeft(y))
                .append("text")
                .attr("transform", "rotate(-90)")
                .attr("y", 6)
                .attr("dy", "0.71em")
                .attr("fill", "#000")
                .text("Temperature, ºF");

            var city = g.selectAll(".city")
                .data(cities)
                .enter().append("g")
                .attr("class", "city");

            city.append("path")
                .attr("class", "line")
                .attr("d", function(d) { return line(d.values); })
                .style("stroke", function(d) { return z(d.id); });

            city.append("text")
                .datum(function(d) { return {id: d.id, value: d.values[d.values.length - 1]}; })
                .attr("transform", function(d) { return "translate(" + x(d.value.date) + "," + y(d.value.temperature) + ")"; })
                .attr("x", 3)
                .attr("dy", "0.35em")
                .style("font", "10px sans-serif")
                .text(function(d) { return d.id; });
        });

        function type(d, _, columns) {
            d.date = parseTime(d.date);
            for (var i = 1, n = columns.length, c; i < n; ++i) d[c = columns[i]] = +d[c];
            return d;
        }
    },
    initChartist: function(){
        var svg = d3.select("#svg1"),
            margin = {top: 0, right: 20, bottom: 30, left: 40},
            width = svg.attr("width") - margin.left - margin.right,
            height = svg.attr("height")
                - margin.top - margin.bottom;

        var x = d3.scaleBand().rangeRound([0, width]).padding(0.1),
            y = d3.scaleLinear().rangeRound([height, 0]);

        var g = svg.append("g")
            .attr("transform", "translate(" + margin.left + "," + margin.top + ")");

        d3.csv("../csv/hbase.csv", function(d) {
            d.Load = +d.Load;     //不懂什么意思这
            return d;
        }, function(error, data) {
            if (error) throw error;
            x.domain(data.map(function(d) {  return d.Name; }));
            y.domain([0, d3.max(data, function(d) { return d.Load; })]);

            g.append("g")
                .attr("class", "axis axis--x")
                .attr("transform", "translate(0," + height + ")")
                .call(d3.axisBottom(x));

            g.append("g")
                .attr("class", "axis axis--y")
                .call(d3.axisLeft(y).ticks(10))
                .append("text")
                .attr("fill", "#000")
                // .attr("transform", "rotate(-90)")
                .attr("transform", "translate(100,0)")
                .attr("y", 6)
                .attr("dy", "0.71em")
                .attr("text-anchor", "end")
                .text("Load(Megawatts)");

            g.selectAll(".bar")
                .data(data)
                .enter().append("g").attr("class","bt")
                .on("mouseover",function(d,i){
                    d3.select(this).append("text")
                        .attr("x",function(d) { return x(d.Name) + x.bandwidth()/4; })
                        .attr("y",function(d) { return y(d.Load); })
                        .attr("dy","1em")
                        .attr("fill","black")
                        .text(d.Load);
                })
                .on("mouseout",function(d,i){
                    d3.select(this)
                        .select("text")
                        .remove();
                })

                .append("rect")
                .attr("class", "bar")
                .attr("x", function(d) { return x(d.Name); })
                .attr("y", function(d) { return y(d.Load); })
                .attr("width", x.bandwidth())
                .attr("height", function(d) { return height - y(d.Load); });

        });

    },
    initPolyLine: function(){
        var svg = d3.select("#svg2"),
            margin = {top: 20, right: 20, bottom: 110, left: 40},
            margin2 = {top: 430, right: 20, bottom: 30, left: 40},
            width = +svg.attr("width") - margin.left - margin.right,
            height = +svg.attr("height") - margin.top - margin.bottom,
            height2 = +svg.attr("height") - margin2.top - margin2.bottom;

        var parseDate = d3.timeParse("%b %Y");

        var x = d3.scaleTime().range([0, width]),
            x2 = d3.scaleTime().range([0, width]),
            y = d3.scaleLinear().range([height, 0]),
            y2 = d3.scaleLinear().range([height2, 0]);

        var xAxis = d3.axisBottom(x),
            xAxis2 = d3.axisBottom(x2),
            yAxis = d3.axisLeft(y);

        var brush = d3.brushX()
            .extent([[0, 0], [width, height2]])
            .on("brush end", brushed);

        var zoom = d3.zoom()
            .scaleExtent([1, Infinity])
            .translateExtent([[0, 0], [width, height]])
            .extent([[0, 0], [width, height]])
            .on("zoom", zoomed);

        var area = d3.area()
            .curve(d3.curveMonotoneX)
            .x(function(d) { return x(d.date); })
            .y0(height)
            .y1(function(d) { return y(d.price); });

        var area2 = d3.area()
            .curve(d3.curveMonotoneX)
            .x(function(d) { return x2(d.date); })
            .y0(height2)
            .y1(function(d) { return y2(d.price); });

        svg.append("defs").append("clipPath")
            .attr("id", "clip")
            .append("rect")
            .attr("width", width)
            .attr("height", height);

        var focus = svg.append("g")
            .attr("class", "focus")
            .attr("transform", "translate(" + margin.left + "," + margin.top + ")");

        var context = svg.append("g")
            .attr("class", "context")
            .attr("transform", "translate(" + margin2.left + "," + margin2.top + ")");

        d3.csv("../csv/sp500.csv", type, function(error, data) {
            if (error) throw error;

            x.domain(d3.extent(data, function(d) { return d.date; }));
            y.domain([0, d3.max(data, function(d) { return d.price; })]);
            x2.domain(x.domain());
            y2.domain(y.domain());

            focus.append("path")
                .datum(data)
                .attr("class", "area")
                .attr("d", area);

            focus.append("g")
                .attr("class", "axis axis--x")
                .attr("transform", "translate(0," + height + ")")
                .call(xAxis);

            focus.append("g")
                .attr("class", "axis axis--y")
                .call(yAxis);

            context.append("path")
                .datum(data)
                .attr("class", "area")
                .attr("d", area2);

            context.append("g")
                .attr("class", "axis axis--x")
                .attr("transform", "translate(0," + height2 + ")")
                .call(xAxis2);

            context.append("g")
                .attr("class", "brush")
                .call(brush)
                .call(brush.move, x.range());

            svg.append("rect")
                .attr("class", "zoom")
                .attr("width", width)
                .attr("height", height)
                .attr("transform", "translate(" + margin.left + "," + margin.top + ")")
                .call(zoom);
        });

        function brushed() {
            if (d3.event.sourceEvent && d3.event.sourceEvent.type === "zoom") return; // ignore brush-by-zoom
            var s = d3.event.selection || x2.range();
            x.domain(s.map(x2.invert, x2));
            focus.select(".area").attr("d", area);
            focus.select(".axis--x").call(xAxis);
            svg.select(".zoom").call(zoom.transform, d3.zoomIdentity
                .scale(width / (s[1] - s[0]))
                .translate(-s[0], 0));
        }

        function zoomed() {
            if (d3.event.sourceEvent && d3.event.sourceEvent.type === "brush") return; // ignore zoom-by-brush
            var t = d3.event.transform;
            x.domain(t.rescaleX(x2).domain());
            focus.select(".area").attr("d", area);
            focus.select(".axis--x").call(xAxis);
            context.select(".brush").call(brush.move, x.range().map(t.invertX, t));
        }

        function type(d) {
            d.date = parseDate(d.date);
            d.price = +d.price;
            return d;
        }

    },
    initLoadLine: function () {
        var capitalRows = null;
        var northRows = null;
        var dom = document.getElementById('loadline');
        var myChart = echarts.init(dom);
        var option = null;

        $.ajax({
            url : '../csv/capital.csv',
            async : false,
            success : function (data) {
                capitalRows = data.split(/\r?\n|\r/);
            }
        });
        $.ajax({
            url : '../csv/north.csv',
            async : false,
            success : function (data) {
                northRows = data.split(/\r?\n|\r/);
            }
        });
        option = {
            title: {
                text: ''
            },
            tooltip: {
                trigger: 'axis'
            },
            legend: {
                data:['capital','north'],
                x: 'left'
            },
            xAxis: {
                data: capitalRows.map(function (item) {
                    return item.split(',')[0];
                })
            },
            yAxis: {
                min: 600,
                splitLine: {
                    show: false
                }
            },
            toolbox: {
                left: 'center',
                feature: {
                    dataZoom: {
                        yAxisIndex: 'none'
                    },
                    restore: {},
                    saveAsImage: {}
                }
            },
            dataZoom: [{
                startValue: '2015-01-01'
            }, {
                type: 'inside'
            }],
            visualMap: {
                top: 10,
                right: 10,
                pieces: [{
                    gt: 0,
                    lte: 300,
                    color: '#096'
                }, {
                    gt: 300,
                    lte: 600,
                    color: '#ffde33'
                }, {
                    gt: 600,
                    lte: 900,
                    color: '#ff9933'
                }, {
                    gt: 900,
                    lte: 1200,
                    color: '#cc0033'
                }, {
                    gt: 1200,
                    lte: 1500,
                    color: '#660099'
                }, {
                    gt: 1500,
                    color: '#7e0023'
                }],
                outOfRange: {
                    color: '#999'
                }
            },
            series: [
                {
                    name: 'capital',
                    type: 'line',
                    data: capitalRows.map(function (item) {
                        return item.split(',')[1];
                    }),
                    markLine: {
                        silent: true,
                        data: [{
                            yAxis: 900
                        }, {
                            yAxis: 1200
                        }, {
                            yAxis: 1500
                        }]
                    }
                },
                {
                    name: 'north',
                    type: 'line',
                    data: northRows.map(function (item) {
                        return item.split(',')[1];
                    }),
                    markLine: {
                        silent: true,
                        data: [{
                            yAxis: 900
                        }, {
                            yAxis: 1200
                        }, {
                            yAxis: 1500
                        }]
                    }
                }
            ]
        };
        if (option && typeof option === "object") {
            myChart.setOption(option, true);
        }
        maxLoad(capitalRows, "capital");
        maxLoad(northRows, "north");
    },
    initLoadPie: function () {
        var dom = document.getElementById('loadpie');
        var myChart = echarts.init(dom);
        var option = null;
        option = {
            backgroundColor: '#797188',
            title: {
            },
            legend: {
                data:['最大用电量','总用电量'],
                x: 'right',
                selected:{
                    '最大用电量': false,
                    '总用电量 ': false
                }
            },
            tooltip : {
                trigger: 'item',
                formatter: "{a} <br/>{b} : {c} ({d}%)"
            },

            visualMap: {
                show: false,
                min: 80,
                max: 600,
                inRange: {
                    colorLightness: [0, 1]
                }
            },
            series : [
                {
                    name:'最大用电量',
                    type:'pie',
                    radius : '55%',
                    center: ['50%', '50%'],
                    data:[
                        {value:dataMap.capitalmax, name:'capital'},
                        {value:dataMap.northmax, name:'north'}
                    ].sort(function (a, b) { return a.value - b.value; }),
                    roseType: 'radius',
                    label: {
                        normal: {
                            textStyle: {
                                color: 'rgba(255, 255, 255, 0.3)'
                            }
                        }
                    },
                    labelLine: {
                        normal: {
                            lineStyle: {
                                color: 'rgba(255, 255, 255, 0.3)'
                            },
                            smooth: 0.2,
                            length: 10,
                            length2: 20
                        }
                    },
                    itemStyle: {
                        normal: {
                            color: '#c23531',
                            shadowBlur: 200,
                            shadowColor: 'rgba(0, 0, 0, 0.5)'
                        }
                    },

                    animationType: 'scale',
                    animationEasing: 'elasticOut',
                    animationDelay: function (idx) {
                        return Math.random() * 200;
                    }
                },
                {
                    name:'总用电量',
                    type:'pie',
                    radius : '65%',
                    center: ['50%', '50%'],
                    data:[
                        {value:dataMap.capitalsum, name:'capital'},
                        {value:dataMap.northsum, name:'north'}
                    ].sort(function (a, b) { return a.value - b.value; }),
                    roseType: 'radius',
                    label: {
                        normal: {
                            textStyle: {
                                color: 'rgba(255, 255, 255, 0.3)'
                            }
                        }
                    },
                    labelLine: {
                        normal: {
                            lineStyle: {
                                color: 'rgba(255, 255, 255, 0.3)'
                            },
                            smooth: 0.2,
                            length: 10,
                            length2: 20
                        }
                    },
                    itemStyle: {
                        normal: {
                            color: '#c23531',
                            shadowBlur: 200,
                            shadowColor: 'rgba(0, 0, 0, 0.5)'
                        }
                    },

                    animationType: 'scale',
                    animationEasing: 'elasticOut',
                    animationDelay: function (idx) {
                        return Math.random() * 200;
                    }
                }
            ]
        };
        if (option && typeof option === "object") {
            myChart.setOption(option, true);
        }
    },

    initWeather: function () {
        // 路径配置
        require.config({
            paths: {
                echarts: 'http://echarts.baidu.com/build/dist'
            }
        });

        // 使用
        require(
            [
                'echarts',
                'echarts/chart/bar', // 使用柱状图就加载bar模块，按需加载
                'echarts/chart/line'
            ],
            function (ec) {

                // 基于准备好的dom，初始化echarts图表
                var myChart1 = ec.init(main1);
                var myChart2 = ec.init(main2);
                var myChart3 = ec.init(main3);
                var myChart4 = ec.init(main4);

                $.ajax({
                    url: '../csv/weather2012.csv',
                    dataType: 'text'
                }).done(successFunction);

                $.ajax({
                    url: '../csv/weather2012_1.csv',
                    dataType: 'text'
                }).done(successFunction2);

                var xdata = new Array();
                var ydata0 = new Array();
                var ydata1 = new Array();
                var ydata3 = new Array();
                var ydata4 = new Array();

                var xdata1 = new Array();
                var ydata01 = new Array();
                var ydata11 = new Array();
                var ydata31 = new Array();
                var ydata41 = new Array();

                function successFunction(data) {

                    var allRows = data.split(/\r?\n|\r/);

                    for (var singleRow = 1; singleRow < 500; singleRow++) {

                        var rowCells = allRows[singleRow].split(',');
                        xdata.push(rowCells[13]);

                        ydata0.push(Number(rowCells[1]));
                        ydata1.push(Number(rowCells[2]));
                        ydata3.push(Number(rowCells[3]));
                        ydata4.push(Number(rowCells[4]));
                    }
                    // 为echarts对象加载数据
                    myChart1.setOption(option1);
                    myChart2.setOption(option2);
                    myChart3.setOption(option3);
                    myChart4.setOption(option4);
                    //联动配置
                    myChart1.connect([myChart2, myChart3, myChart4]);
                    myChart2.connect([myChart1, myChart3, myChart4]);
                    myChart3.connect([myChart2, myChart1, myChart4]);
                    myChart4.connect([myChart2, myChart3, myChart1]);
                }

                function successFunction2(data) {

                    var allRows = data.split(/\r?\n|\r/);

                    for (var singleRow = 1; singleRow < 500; singleRow++) {

                        var rowCells = allRows[singleRow].split(',');
                        xdata1.push(rowCells[13]);

                        ydata01.push(Number(rowCells[1]) * 0.5);
                        ydata11.push(Number(rowCells[2]) * 0.5);
                        ydata31.push(Number(rowCells[3]) * 0.5);
                        ydata41.push(Number(rowCells[4]) * 0.5);
                    }
                    // 为echarts对象加载数据
                    myChart1.setOption(option1);
                    myChart2.setOption(option2);
                    myChart3.setOption(option3);
                    myChart4.setOption(option4);
                    //联动配置
                    myChart1.connect([myChart2, myChart3, myChart4]);
                    myChart2.connect([myChart1, myChart3, myChart4]);
                    myChart3.connect([myChart2, myChart1, myChart4]);
                    myChart4.connect([myChart2, myChart3, myChart1]);
                }

                var option1 = {
                    title: {
                        text: '温度'
                    },
                    tooltip: {
                        trigger: 'axis'
                    },
                    legend: {
                        data: ['北京', '天津']
                    },
                    toolbox: {
                        show: true,
                        feature: {
                            dataZoom: {show: true},
                            restore: {show: true},
                            saveAsImage: {show: true}
                        }
                    },
                    dataZoom: {//滑动条，联动关联
                        backgroundColor: 'rgba(0,0,0,0)',
                        dataBackgroundColor: 'rgba(0,0,0,0)',
                        handleColor: 'rgba(70,130,180,0.8)',
                        fillerColor: 'rgba(144,197,237,0.6)',
                        handleSize: 7,
                        show: false,//不显示，已经做关联，由最后一个图显示
                        realtime: true,
                        start: 0,
                        end: 100
                    },
                    xAxis: [
                        {
                            type: 'category', //x轴为类目类型
                            data: xdata
                        }
                    ],
                    yAxis: [
                        {
                            type: 'value'  //y轴为值类型
                        }
                    ],
                    series: [{
                        name: '北京',
                        type: 'line',
                        smooth: true,
                        data: ydata0
                    },
                        {
                            name: '天津',
                            type: 'line',
                            smooth: true,
                            data: ydata01
                        }
                    ]
                };

                var option2 = {
                    title: {
                        text: '湿度'
                    },
                    tooltip: {
                        trigger: 'axis'
                    },
                    legend: {
                        y: -30,
                        data: ['北京', '天津']
                    },
                    toolbox: {
                        y: -30,
                        show: true,
                        feature: {
                            dataZoom: {show: true},
                            restore: {show: true},
                            saveAsImage: {show: true}
                        }
                    },
                    dataZoom: {
                        backgroundColor: 'rgba(0,0,0,0)',
                        dataBackgroundColor: 'rgba(0,0,0,0)',
                        handleColor: 'rgba(70,130,180,0.8)',
                        fillerColor: 'rgba(144,197,237,0.6)',
                        handleSize: 7,
                        show: false,
                        realtime: true,
                        start: 0,
                        end: 100
                    },
                    xAxis: [
                        {
                            type: 'category', //x轴为类目类型
                            data: xdata

                        }
                    ],
                    yAxis: [
                        {
                            type: 'value'  //y轴为值类型
                        }
                    ],
                    series: [{
                        name: '北京',
                        type: 'line',
                        smooth: true,
                        data: ydata1
                    },
                        {
                            name: '天津',
                            type: 'line',
                            smooth: true,
                            data: ydata11
                        }
                    ]
                };

                var option3 = {
                    title: {
                        text: '结露点'
                    },
                    tooltip: {
                        trigger: 'axis'
                    },
                    legend: {
                        y: -30,
                        data: ['北京', '天津']
                    },
                    toolbox: {
                        y: -30,
                        show: true,
                        feature: {
                            dataZoom: {show: true},
                            restore: {show: true},
                            saveAsImage: {show: true}
                        }
                    },
                    dataZoom: {
                        backgroundColor: 'rgba(0,0,0,0)',
                        dataBackgroundColor: 'rgba(0,0,0,0)',
                        handleColor: 'rgba(70,130,180,0.8)',
                        fillerColor: 'rgba(144,197,237,0.6)',
                        handleSize: 7,
                        show: false,
                        realtime: true,
                        start: 0,
                        end: 100
                    },
                    xAxis: [
                        {
                            type: 'category', //x轴为类目类型
                            data: xdata

                        }
                    ],
                    yAxis: [
                        {
                            type: 'value'  //y轴为值类型
                        }
                    ],
                    series: [{
                        name: '北京',
                        type: 'line',
                        smooth: true,
                        data: ydata3
                    },
                        {
                            name: '天津',
                            type: 'line',
                            smooth: true,
                            data: ydata31
                        }
                    ]
                };

                var option4 = {
                    title: {
                        text: '风速'
                    },
                    tooltip: {
                        trigger: 'axis'
                    },
                    legend: {
                        y: -30,
                        data: ['北京', '天津']
                    },
                    toolbox: {
                        y: -30,
                        show: true,
                        feature: {
                            dataZoom: {show: true},
                            restore: {show: true},
                            saveAsImage: {show: true}
                        }
                    },
                    dataZoom: {
                        //y:350,
                        backgroundColor: 'rgba(0,0,0,0)',
                        dataBackgroundColor: 'rgba(0,0,0,0)',
                        handleColor: 'rgba(79,130,180,0.8)',
                        fillerColor: 'rgba(144,197,237,0.6)',
                        handleSize: 17,
                        show: true,//显示
                        realtime: true,
                        start: 0,
                        end: 100
                    },
                    xAxis: [
                        {
                            type: 'category', //x轴为类目类型
                            data: xdata

                        }
                    ],
                    yAxis: [
                        {
                            type: 'value'  //y轴为值类型
                        }
                    ],

                    series: [{
                        name: '北京',
                        type: 'line',
                        smooth: true,
                        data: ydata4
                    },
                        {
                            name: '天津',
                            type: 'line',
                            smooth: true,
                            data: ydata41
                        }
                    ]
                }
            }
        )
    },

	showNotification: function(from, align){
    	color = Math.floor((Math.random() * 4) + 1);

    	$.notify({
        	icon: "pe-7s-gift",
        	message: "Welcome to <b>Light Bootstrap Dashboard</b> - a beautiful freebie for every web developer."

        },{
            type: type[color],
            timer: 4000,
            placement: {
                from: from,
                align: align
            }
        });
	}
};

