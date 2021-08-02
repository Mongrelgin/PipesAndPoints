import Plotly from 'plotly.js-dist-min/plotly.min.js';

let allPlots = [];

let layout = {
    title: '3D Line Plot',
    autosize: false,
    width: 500,
    height: 500,
    margin: {
        l: 0,
        r: 0,
        b: 0,
        t: 65
    }
};

window.clean = function clean() {
    allPlots = [];
}

window.start = function start(graphName, xMass, yMass, zMass) {
    let simplePlot = {
        name: graphName,
        type: 'scatter3d',
        mode: 'lines+markers',
        x: xMass,
        y: yMass,
        z: zMass,
        line: {
            width: 6,
            color: 'rgb(0,0,0)',
            colorscale: "Viridis"},
        marker: {
            size: 3.5,
            color: 'rgb(2,200,100)',
            colorscale: "Greens",
            cmin: -20,
            cmax: 50
        }
    };

    allPlots.push(simplePlot);
    console.log(allPlots);
    Plotly.newPlot('pipesPlot', allPlots, layout);
}