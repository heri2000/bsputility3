/*
 * Created on Sep 12, 2006
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.beepsoft.tablegoodies;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;

import javax.swing.table.DefaultTableCellRenderer;

/**
 * @author Her
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class FractionCellRenderer extends DefaultTableCellRenderer {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public FractionCellRenderer(int integer, int fraction, int align) {
	  this.integer = integer; // maximum integer digits
	  this.minFraction = fraction; // minimum number of fraction digits
          this.maxFraction = fraction; // maximum number of fraction digits
	  this.align = align; // alignment (LEFT, CENTER, RIGHT)
	}

	public FractionCellRenderer(int integer, int minFraction, int maxFraction, int align) {
	  this.integer = integer; // maximum integer digits
	  this.minFraction = minFraction; // minimum number of fraction digits
          this.maxFraction = maxFraction; // maximum number of fraction digits
	  this.align = align; // alignment (LEFT, CENTER, RIGHT)
	}

	protected void setValue(Object value) {
	  if (value != null && value instanceof Number) {
			DecimalFormatSymbols dfs= new DecimalFormatSymbols();
			dfs.setDecimalSeparator(',');
			dfs.setGroupingSeparator('.');
	  	formatter.setDecimalFormatSymbols(dfs);
			formatter.setMaximumIntegerDigits(integer);
			formatter.setMaximumFractionDigits(maxFraction);
			formatter.setMinimumFractionDigits(minFraction);
			setText(formatter.format(((Number) value).doubleValue()));
	  } else {
			super.setValue(value);
	  }
	  setHorizontalAlignment(align);
	}

	protected int integer;

	protected int minFraction, maxFraction;

	protected int align;

	protected static DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance();
}
