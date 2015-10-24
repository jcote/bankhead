angular.module('bankheadApp')
    .controller('VoteDemoCtrl', ['$scope', '$interval', 'VoteChart', function($scope, $interval, VoteChart){
        Highcharts.setOptions({
            global: {
                useUTC: false
            }
        });

        $scope.incYay = function() {
            VoteChart.incYays();
        };
        $scope.incNay = function() {
            VoteChart.incNays();
        };

        $scope.chartConfig = {
            options: {
                chart: {
                    type: 'spline',
                    animation: Highcharts.svg, // don't animate in old IE
                    marginRight: 10,
                    events: {
                        load: function() {
        
                        }
                    }
                },
                tooltip: {
                    formatter: function() {
                            return '<b>'+ this.series.name +'</b><br/>'+
                            Highcharts.dateFormat('%Y-%m-%d %H:%M:%S', this.x) +'<br/>'+
                            Highcharts.numberFormat(this.y, 2);
                    }
                },
                legend: {
                    enabled: false
                },
                exporting: {
                    enabled: false
                },
            },
            title: {
                text: 'Live random data'
            },
            xAxis: {
                type: 'datetime',
                tickPixelInterval: 150
            },
            yAxis: {
                title: {
                    text: 'Value'
                },
                plotLines: [{
                    value: 0,
                    width: 1,
                    color: '#808080'
                }]
            },
            series: [{
                name: 'Yays',
                data: []
            },
            {
                name: 'Nays',
                data: []
            }]
        };


        // set up the updating of the chart each second
        $interval(function() {
            var series = $scope.chartConfig.series;
            VoteChart.updateChart(series);
        }, 2000);

    }]);
