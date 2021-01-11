package org.unibl.etf.beans;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;

import java.awt.Desktop.Action;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.primefaces.model.charts.ChartData;
import org.primefaces.model.charts.ChartModel;
import org.primefaces.model.charts.axes.cartesian.CartesianScales;
import org.primefaces.model.charts.axes.cartesian.linear.CartesianLinearAxes;
import org.primefaces.model.charts.axes.cartesian.linear.CartesianLinearTicks;
import org.primefaces.model.charts.bar.BarChartDataSet;
import org.primefaces.model.charts.bar.BarChartModel;
import org.primefaces.model.charts.bar.BarChartOptions;
import org.primefaces.model.charts.optionconfig.legend.Legend;
import org.primefaces.model.charts.optionconfig.legend.LegendLabel;
import org.primefaces.model.charts.optionconfig.title.Title;
import org.unibl.etf.dao.TokenDAO;
import org.unibl.etf.dto.CharJs;


@ManagedBean(name = "charJsBean")
@RequestScoped
public class ChartJsBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private BarChartModel barModel = new BarChartModel();

    @PostConstruct
    public void init() {
    	ArrayList<CharJs> charJsArrayList = new ArrayList<CharJs>();
    	charJsArrayList = TokenDAO.getNumberOfUsersPerHours();
        ChartData data = new ChartData();

        BarChartDataSet barDataSet = new BarChartDataSet();
        barDataSet.setLabel("Users activity in the past 24 hours");

        List<Number> values = new ArrayList<>();
        List<String> labels = new ArrayList<>();
        
        charJsArrayList.forEach(action -> {
        	Number number = action.getUsersPerHour();
        	values.add(number);
        	String hour = action.getHour().toString();
        	
        	if(hour.equals("01")) {
        		labels.add("01-02 [h]");
        	}
        	else if(hour.equals("02")) {
            labels.add("02-03 [h]");
        	}
        	else if(hour.equals("03")) {
            labels.add("03-04 [h]");
        	}
        	else if(hour.equals("04")) {
            labels.add("04-05 [h]");
        	}
        	else if(hour.equals("05")) {
            labels.add("05-06 [h]");
        	}
        	else if(hour.equals("06")) {
            labels.add("06-07 [h]");
        	}
        	else if(hour.equals("07")) {
            labels.add("07-08 [h]");
        	}
        	else if(hour.equals("08")) {
            labels.add("08-09 [h]");
        	}
        	else if(hour.equals("09")) {
            labels.add("09-10 [h]");
        	}
        	else if(hour.equals("10")) {
            labels.add("10-11 [h]");
        	}
        	else if(hour.equals("11")) {
            labels.add("11-12 [h]");
        	}
        	else if(hour.equals("12")) {
            labels.add("12-13 [h]");
        	}
        	else if(hour.equals("13")) {
            labels.add("13-14 [h]");
        	}
        	else if(hour.equals("14")) {
            labels.add("14-15 [h]");
        	}
        	else if(hour.equals("15")) {
            labels.add("15-16 [h]");
        	}
        	else if(hour.equals("16")) {
            labels.add("16-17 [h]");
        	}
        	else if(hour.equals("17")) {
            labels.add("17-18 [h]");
        	}
        	else if(hour.equals("18")) {
            labels.add("18-19 [h]");
        	}
        	else if(hour.equals("19")) {
            labels.add("19-20 [h]");
        	}
        	else if(hour.equals("20")) {
            labels.add("20-21 [h]");
        	}
        	else if(hour.equals("21")) {
            labels.add("21-22 [h]");
        	}
        	else if(hour.equals("22")) {
            labels.add("22-23 [h]");
        	}
        	else if(hour.equals("23")) {
            labels.add("23-00 [h]");
        	}
        });
               
        barDataSet.setData(values);

        List<String> bgColor = new ArrayList<>();
        bgColor.add("rgba(255, 99, 132, 0.2)");
        bgColor.add("rgba(255, 159, 64, 0.2)");
        bgColor.add("rgba(255, 205, 86, 0.2)");
        bgColor.add("rgba(75, 192, 192, 0.2)");
        bgColor.add("rgba(54, 162, 235, 0.2)");
        bgColor.add("rgba(153, 102, 255, 0.2)");
        bgColor.add("rgba(201, 203, 207, 0.2)");

        bgColor.add("rgba(255, 99, 132, 0.2)");
        bgColor.add("rgba(255, 159, 64, 0.2)");
        bgColor.add("rgba(255, 205, 86, 0.2)");
        bgColor.add("rgba(75, 192, 192, 0.2)");
        bgColor.add("rgba(54, 162, 235, 0.2)");
        bgColor.add("rgba(153, 102, 255, 0.2)");
        bgColor.add("rgba(201, 203, 207, 0.2)");
        
        bgColor.add("rgba(255, 99, 132, 0.2)");
        bgColor.add("rgba(255, 159, 64, 0.2)");
        bgColor.add("rgba(255, 205, 86, 0.2)");
        bgColor.add("rgba(75, 192, 192, 0.2)");
        bgColor.add("rgba(54, 162, 235, 0.2)");
        bgColor.add("rgba(153, 102, 255, 0.2)");
        bgColor.add("rgba(201, 203, 207, 0.2)");
        
        bgColor.add("rgba(54, 162, 235, 0.2)");
        bgColor.add("rgba(153, 102, 255, 0.2)");
        bgColor.add("rgba(201, 203, 207, 0.2)");
        
        barDataSet.setBackgroundColor(bgColor);

        List<String> borderColor = new ArrayList<>();
        borderColor.add("rgb(255, 99, 132)");
        borderColor.add("rgb(255, 159, 64)");
        borderColor.add("rgb(255, 205, 86)");
        borderColor.add("rgb(75, 192, 192)");
        borderColor.add("rgb(54, 162, 235)");
        borderColor.add("rgb(153, 102, 255)");
        borderColor.add("rgb(201, 203, 207)");
        
        borderColor.add("rgb(255, 99, 132)");
        borderColor.add("rgb(255, 159, 64)");
        borderColor.add("rgb(255, 205, 86)");
        borderColor.add("rgb(75, 192, 192)");
        borderColor.add("rgb(54, 162, 235)");
        borderColor.add("rgb(153, 102, 255)");
        borderColor.add("rgb(201, 203, 207)");
        
        borderColor.add("rgb(255, 99, 132)");
        borderColor.add("rgb(255, 159, 64)");
        borderColor.add("rgb(255, 205, 86)");
        borderColor.add("rgb(75, 192, 192)");
        borderColor.add("rgb(54, 162, 235)");
        borderColor.add("rgb(153, 102, 255)");
        borderColor.add("rgb(201, 203, 207)");
        
        borderColor.add("rgb(54, 162, 235)");
        borderColor.add("rgb(153, 102, 255)");
        borderColor.add("rgb(201, 203, 207)");
        barDataSet.setBorderColor(borderColor);
        barDataSet.setBorderWidth(1);

        data.addChartDataSet(barDataSet);
       
        data.setLabels(labels);

        //Data
        barModel.setData(data);

        //Options
        BarChartOptions options = new BarChartOptions();
        CartesianScales cScales = new CartesianScales();
        CartesianLinearAxes linearAxes = new CartesianLinearAxes();
        CartesianLinearTicks ticks = new CartesianLinearTicks();
        ticks.setBeginAtZero(true);
        linearAxes.setTicks(ticks);
        cScales.addYAxesData(linearAxes);
        options.setScales(cScales);

        Title title = new Title();
        title.setDisplay(true);
        title.setText("Bar Chart");
        options.setTitle(title);

        Legend legend = new Legend();
        legend.setDisplay(true);
        legend.setPosition("top");
        LegendLabel legendLabels = new LegendLabel();
        legendLabels.setFontStyle("bold");
        legendLabels.setFontColor("#2980B9");
        legendLabels.setFontSize(24);
        legend.setLabels(legendLabels);
        options.setLegend(legend);

        barModel.setOptions(options);
    }

    public BarChartModel getBarModel() {
        return barModel;
    }
}
	
