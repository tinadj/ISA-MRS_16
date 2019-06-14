<template>
    <b-container>
    <b-row>
        <b-col lg="4">
            <b-form-group 
                label-cols-sm="4"
                label-cols-lg="4"
                label="Choose dates: "
                >
                <v-date-picker mode="range" v-model="dates"/>
            </b-form-group>
        </b-col>

        <b-col lg="1">
            <b-button variant="primary" v-on:click="show">Show</b-button>
        </b-col>
        <b-col lg="1">
            <b-form-radio-group
                v-model="reportType"
                :options="types"
                buttons
                button-variant="outline-secondary"
            ></b-form-radio-group>
        </b-col>
    </b-row>
    <b-row>
        <b-col>
            <div class="control-section">
                <div align='center'>
                <ejs-chart style='display:block' :theme='theme' align='center' id='chartcontainer' :title='title' :primaryXAxis='primaryXAxis' :primaryYAxis='primaryYAxis'
                    :tooltip='tooltip' :chartArea='chartArea' :width='width'>
                    <e-series-collection>
                        <e-series :dataSource='seriesData' type='Line' xName='x' yName='y' name='Number of rented vehicles' width=2 :marker='marker'> </e-series>
                    </e-series-collection>
                </ejs-chart>
                </div>
            </div>
        </b-col>
    </b-row>

    </b-container>
</template>

<script>
import {AXIOS} from '../../http-common'

import Vue from "vue";
import { Browser } from '@syncfusion/ej2-base';
import { ChartPlugin, LineSeries, Legend, Tooltip, DateTime } from "@syncfusion/ej2-vue-charts";

Vue.use(ChartPlugin);

let selectedTheme = location.hash.split("/")[1];
selectedTheme = selectedTheme ? selectedTheme : "Material";
let theme = (selectedTheme.charAt(0).toUpperCase() + selectedTheme.slice(1)).replace(/-dark/i, "Dark");

export default Vue.extend({
    name: "RACDaily",
    data: function() {
        return {
        dates: null,
        reportType: 0,
        types: [
            { text: "Daily", value: 0},
            { text: "Weekly", value: 1},
            { text: "Monthly", value: 2}
        ],
        theme: theme,
        seriesData: [
        ],
        //Initializing Primary X Axis
        primaryXAxis: {
            valueType: "DateTime",
            labelFormat: "d-M-y",
            majorGridLines: { width: 0 }
        },
        //Initializing Primary Y Axis
        primaryYAxis: {
            labelFormat: "{value}",
            rangePadding: "None",
            minimum: 0,
            maximum: 50,
            interval: 5,
            lineStyle: { width: 0 },
            majorTickLines: { width: 0 },
            minorTickLines: { width: 0 }
        },
        chartArea: {
            border: {
            width: 0
            }
        },
        width : Browser.isDevice ? '100%' : '60%',
        marker: {
            visible: false,
            height: 10,
            width: 10
        },
        tooltip: {
            enable: true
        },
        title: ""
        };
    },
    provide: {
        chart: [LineSeries, Legend, Tooltip, DateTime]
    },
    methods: {
        show: function(e) {
            e.preventDefault();
            this.seriesData = []

            if (this.dates != null) {
                let strStartDate = this.dates.start.getFullYear() + "-" + (this.dates.start.getMonth()+1) + "-" +  this.dates.start.getDate()
                let strEndDate = this.dates.end.getFullYear() + "-" + (this.dates.end.getMonth()+1) + "-" +  this.dates.end.getDate()

                let dateCounter = this.dates.start

                // Daily resrved vehicles
                if (this.reportType == 0) {
                    this.primaryXAxis.labelFormat = "d-M-y"
                    this.marker.visible = false

                    AXIOS.get("/rac-reservations/report-daily/" + this.$route.params.id + "/" + strStartDate + "/" + strEndDate)
                    .then( response => {
                        for (let i = 0; i < response.data.length; i++) {
                            this.seriesData.push(
                                {x: new Date(dateCounter.getFullYear(), dateCounter.getMonth(), dateCounter.getDate()), y: response.data[i]}
                            )
                            dateCounter = this.addDays(dateCounter, 1)
                        }
                    })
                    .catch(err => console.log(err))
                
                // Weekly reserved vehicles
                } else if (this.reportType == 1) {
                    this.primaryXAxis.labelFormat = "d-M-y"
                    this.marker.visible = true

                    AXIOS.get("/rac-reservations/report-weekly/" + this.$route.params.id + "/" + strStartDate + "/" + strEndDate)
                    .then( response => {
                        for (let i = 0; i < response.data.length; i++) {
                            this.seriesData.push(
                                {x: new Date(dateCounter.getFullYear(), dateCounter.getMonth(), dateCounter.getDate()), y: response.data[i]}
                            )
                            dateCounter = this.addDays(dateCounter, 7)
                        }
                    })
                    .catch(err => console.log(err))
                
                // Monthly reserved vehicles
                } else {
                    this.primaryXAxis.labelFormat = "M y"
                    this.marker.visible = true

                    AXIOS.get("/rac-reservations/report-monthly/" + this.$route.params.id + "/" + strStartDate + "/" + strEndDate)
                    .then( response => {
                        for (let i = 0; i < response.data.length; i++) {
                            this.seriesData.push(
                                {x: new Date(dateCounter.getFullYear(), dateCounter.getMonth(), dateCounter.getDate()), y: response.data[i]}
                            )
                            dateCounter = this.nextMonth(dateCounter)
                        }
                        
                    })
                    .catch(err => console.log(err))
                }
            }
            
        },
        addDays: function(date, days) {
            var result = new Date(date);
            result.setDate(result.getDate() + days);
            return result;
        },
        nextMonth: function(date) {
                if (date.getMonth() == 11) {
                    return new Date(date.getFullYear() + 1, 0, 1);
                } else {
                    return new Date(date.getFullYear(), date.getMonth() + 1, 1);
                }
        }
    },
    mounted() {

    }   
})
</script>

