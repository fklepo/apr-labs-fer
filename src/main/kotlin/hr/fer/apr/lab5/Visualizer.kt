package hr.fer.apr.lab5

import org.jfree.chart.ChartFactory
import org.jfree.chart.ChartPanel
import org.jfree.chart.plot.PlotOrientation
import org.jfree.chart.ui.ApplicationFrame
import org.jfree.data.xy.XYSeries
import org.jfree.data.xy.XYSeriesCollection
import java.awt.Dimension
import java.io.File
import java.lang.IllegalArgumentException

class Visualizer: ApplicationFrame {

    constructor(path: String) : super("Visualizer") {
        val x1Series = XYSeries("x1")
        val x2Series = XYSeries("x2")
        for (line in File(path).readLines()) {
            val lineChunks = line.split(",")
            x1Series.add(lineChunks[0].toDouble(), lineChunks[1].toDouble())
            x2Series.add(lineChunks[0].toDouble(), lineChunks[2].toDouble())
        }
        val data = XYSeriesCollection()
        data.addSeries(x1Series)
        data.addSeries(x2Series)
        val chart = ChartFactory.createXYLineChart("Visualizer", "t", "x(t)", data,
                PlotOrientation.VERTICAL, true, true, false)

        val chartPanel = ChartPanel(chart)
        chartPanel.setPreferredSize(Dimension(500, 300))
        contentPane = chartPanel
    }
}

fun main(args: Array<String>) {
    if (args.size != 1) {
        throw IllegalArgumentException("Expected one argument, file path")
    }
    val visualizer = Visualizer(args[0])
    visualizer.pack()
    visualizer.isVisible = true

}