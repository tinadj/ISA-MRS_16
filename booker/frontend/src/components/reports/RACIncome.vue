<template>
    <b-container>
    <b-row>
        <b-col lg="2">
            <v-date-picker mode="range" v-model="dates"/>
        </b-col>

        <b-col lg="1">
            <b-button variant="outline-primary" v-on:click="show">Show</b-button>
        </b-col>
    </b-row>
    <b-row>
        <b-col>
            <div class="control-section">
                <div align='center'>
                <ejs-chart style='display:block' :theme='theme' align='center' id='chartcontainer' :title='title' :primaryXAxis='primaryXAxis' :primaryYAxis='primaryYAxis'
                    :tooltip='tooltip' :chartArea='chartArea' :width='width'>
                    <e-series-collection>
                        <e-series :dataSource='seriesData' type='Line' xName='x' yName='y' name='Germany' width=2 :marker='marker'> </e-series>
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
export default {
    name: "RACIncome",
    data() {
        return {
            dates: '',
            title: 'Rent a Car Income'
        }
    },
    methods: {

    }, 
    mounted() {

    }
       
}
</script>

