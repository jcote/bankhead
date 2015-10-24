angular.module('bankheadApp')
    .service('VoteChart', function(){


        var voteChart = {};
        voteChart.yays = 0;
        voteChart.nays = 0;
        voteChart.stagedYays = 0;
        voteChart.stagedNays = 0;
    
        voteChart.incYays = function() {
            voteChart.stagedYays++;
        };
        voteChart.incNays = function() {
            voteChart.stagedNays++;
        };

        voteChart.updateChart = updateChart;
        function updateChart(series) {
                var date = (new Date()).getTime();
                //yays
                voteChart.yays += voteChart.stagedYays;
                voteChart.stagedYays = 0;
                var xYay = date,
                    yYay = voteChart.yays;
                 series[0].data.push([xYay, yYay]);
                // nays
                voteChart.nays += voteChart.stagedNays;
                voteChart.stagedNays = 0;
                var xNay = date,
                    yNay = voteChart.nays;
                series[1].data.push([xNay, yNay]);
        }


        return voteChart;

    });

