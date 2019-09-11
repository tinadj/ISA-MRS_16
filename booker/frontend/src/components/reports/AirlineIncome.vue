<template>
    <b-container>
    <b-row>
        <b-col lg="7">
            <b-form-radio-group
                v-model="selectedMonth"
                :options="months"
                buttons
                button-variant="outline-secondary"
            ></b-form-radio-group>
        </b-col>
        <b-col lg="1.5">
            <b-input type="number" v-model="year" placeholder="Year" min="1990" max="2035"></b-input>
        </b-col>
        <b-col lg="1">
            <b-button variant="primary" v-on:click="show">Show</b-button>
        </b-col>
    </b-row>
    <b-row>
        <b-col>
            <br>
            <div class="control-section">
                <div align='center'>
                <ejs-chart style='display:block' :theme='theme' align='center' id='chartcontainer' :title='title' :primaryXAxis='primaryXAxis' :primaryYAxis='primaryYAxis'
                    :tooltip='tooltip' :chartArea='chartArea' :width='width'>
                    <e-series-collection>
                        <e-series :dataSource='seriesData' type='Line' xName='x' yName='y' name='Income' width=2 :marker='marker'> </e-series>
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
    name: "AirlineDaily",
    data: function() {
        return {
        selectedMonth: 0,
        year: 2019,
        months: [
            { text: "Jan", value: 0},
            { text: "Feb", value: 1},
            { text: "Mar", value: 2},
            { text: "Apr", value: 3},
            { text: "May", value: 4},
            { text: "Jun", value: 5},
            { text: "Jul", value: 6},
            { text: "Aug", value: 7},
            { text: "Sep", value: 8},
            { text: "Oct", value: 9},
            { text: "Nov", value: 10},
            { text: "Dec", value: 11}
        ],
        theme: theme,
        seriesData: [
        ],
        //Initializing Primary X Axis
        primaryXAxis: {
            valueType: "DateTime",
            labelFormat: "d-M-y",
            edgeLabelPlacement: "Shift",
            majorGridLines: { width: 0 }
        },
        //Initializing Primary Y Axis
        primaryYAxis: {
            labelFormat: "{value}",
            rangePadding: "None",
            minimum: 0,
            maximum: 3000,
            interval: 200,
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
        title: '',
        totalIncome: ''
        };
    },
    provide: {
        chart: [LineSeries, Legend, Tooltip, DateTime]
    },
    methods: {
        show: function(e) {
            e.preventDefault();
            this.seriesData = []
            this.totalIncome = 0;

            let dateCounter = new Date(this.year, this.selectedMonth, 1)

            if (this.year.length != 0) {
                AXIOS.get("/flight-reservations/report-income/" + this.$route.params.id + "/" + this.selectedMonth + "/" + this.year)
                .then( response => {
                    for (let i = 0; i < response.data.length; i++) {
                        this.seriesData.push(
                        {x: dateCounter, y: response.data[i]}
                        )
                        this.totalIncome += response.data[i]

                        dateCounter = this.addDays(dateCounter, 1)
                    }

                    this.title = "Total income for selected month is: " + this.totalIncome
                })
                .catch(err => console.log(err))
            }
        },
        addDays: function(date, days) {
            var result = new Date(date);
            result.setDate(result.getDate() + days);
            return result;
        }
    },
    mounted() {
        this.totalIncome = 0
        this.title = "Total income for selected month is: " + this.totalIncome

    }
})
</script>

