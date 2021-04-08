/*
 * BspDate.java
 *
 * Created on January 19, 2008, 7:55 PM
 */

package com.beepsoft.ptools;

import java.awt.Color;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

/**
 *
 * @author  heri
 */
public class BspDate extends javax.swing.JDialog {
    
    private String selDate, curDate;
    
    private boolean proceed = false;
    
    private ActionListener actionListener;
    private KeyAdapter keyAdapter;
    private FocusAdapter focusAdapter;
    
    private JLabel[] headerLabels, blankLabels;
    private JButton[] calendarButtons;
    
    private Color selectionBacground, lastBackground;
    
    private int selectedCalendarButton = -1;
    private int maxDayCount = 0;
    
    private Timer focusTimer;
    
    private JTextField dateField;
    private JLabel dateLabel;
    
    private static final String[] monthName_ID = {
        "Januari",
        "Pebruari",
        "Maret",
        "April",
        "Mei",
        "Juni",
        "Juli",
        "Agustus",
        "September",
        "Oktober",
        "Nopember",
        "Desember",
        };
    
    /**
     * Creates new form BspDate
     */
    public BspDate(Frame parent) {
        super(parent, true);
        this.dateField = null;
        initComponents();
        initDialog();
    }
    
    public BspDate(Dialog parent) {
        super(parent, true);
        this.dateField = null;
        initComponents();
        initDialog();
    }
    
    private void initDialog() {
        actionListener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String[] dateArr = selDate.split("/");
                int day = BspUtility.parseInt(e.getActionCommand());
                int month = BspUtility.parseInt(dateArr[1]);
                int year = BspUtility.parseInt(dateArr[0]);
                selDate = year + "-" + month + "-" + day;
                proceed = true;
                setVisible(false);
                if (dateField != null) {
                    dateField.setText(swapDate(selDate));
                    dateField = null;
                }
                if (dateLabel != null) {
                    dateLabel.setText(swapDate(selDate));
                    dateLabel.repaint();
                    dateLabel = null;
                }
            }
        };
        
        keyAdapter = new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                int keyCode = e.getKeyCode();
                switch (keyCode) {
                    case KeyEvent.VK_UP:
                        if (selectedCalendarButton > 6) {
                            selectedCalendarButton -= 7;
                        } else {
                            prevMonthButtonActionPerformed(null);
                            selectedCalendarButton = maxDayCount - 7 + selectedCalendarButton;
                        }
                        calendarButtons[selectedCalendarButton].requestFocusInWindow();
                        break;
                    case KeyEvent.VK_DOWN:
                        if (selectedCalendarButton < maxDayCount-7) {
                            selectedCalendarButton += 7;
                        } else {
                            selectedCalendarButton = selectedCalendarButton + 7 - maxDayCount;
                            nextMonthButtonActionPerformed(null);
                        }
                        calendarButtons[selectedCalendarButton].requestFocusInWindow();
                        break;
                    case KeyEvent.VK_LEFT:
                        if (selectedCalendarButton > 0) {
                            selectedCalendarButton--;
                        } else {
                            prevMonthButtonActionPerformed(null);
                            selectedCalendarButton = maxDayCount-1;
                        }
                        calendarButtons[selectedCalendarButton].requestFocusInWindow();
                        break;
                    case KeyEvent.VK_RIGHT:
                        if (selectedCalendarButton < maxDayCount-1) {
                            selectedCalendarButton++;
                        } else {
                            nextMonthButtonActionPerformed(null);
                            selectedCalendarButton = 0;
                        }
                        calendarButtons[selectedCalendarButton].requestFocusInWindow();
                        break;
                    case KeyEvent.VK_ENTER:
                        String[] dateArr = selDate.split("/");
                        int day = BspUtility.parseInt(((JButton) e.getComponent()).getText());
                        int month = BspUtility.parseInt(dateArr[1]);
                        int year = BspUtility.parseInt(dateArr[0]);
                        selDate = year + "-" + month + "-" + day;
                        proceed = true;
                        setVisible(false);
                        if (dateField != null) {
                            dateField.setText(swapDate(selDate));
                            dateField = null;
                        }
                        if (dateLabel != null) {
                            dateLabel.setText(swapDate(selDate));
                            dateLabel.repaint();
                            dateLabel = null;
                        }
                        break;
                }
            }
        };
        
        selectionBacground = new Color(0xCC, 0xCC, 0xFF);
        
        focusAdapter = new FocusAdapter() {
            public void focusGained(FocusEvent e) {
                if (e.getSource().getClass().equals(JButton.class)) {
                    JButton btn = (JButton) e.getSource();
                    lastBackground = btn.getBackground();
                    btn.setBackground(selectionBacground);
                    selectedCalendarButton = BspUtility.parseInt(btn.getText())-1;
                }
            }
            public void focusLost(FocusEvent e) {
                if (e.getSource().getClass().equals(JButton.class)) {
                    JButton btn = (JButton) e.getSource();
                    btn.setBackground(lastBackground);
                }
            }
        };
        
        headerLabels = new JLabel[7];
        for (int i=1; i < 7; i++) {
            headerLabels[i-1] = new JLabel(getDayNameShort(i));
            headerLabels[i-1].setHorizontalAlignment(JLabel.CENTER);
        }
        headerLabels[6] = new JLabel(getDayNameShort(0));
        headerLabels[6].setHorizontalAlignment(JLabel.CENTER);
        headerLabels[6].setForeground(Color.red);
        
        blankLabels = new JLabel[14];
        for (int i=0; i < 14; i++) {
            blankLabels[i] = new JLabel();
        }
        
        calendarButtons = new JButton[31];
        JButton tglButton;
        Insets ins = new Insets(1, 1, 2, 1);
        for (int i=1; i <= 31; i++) {
            tglButton = new JButton(String.valueOf(i));
            tglButton.setMargin(ins);
            tglButton.setFont(tglButton.getFont().deriveFont(Font.PLAIN));
            tglButton.addActionListener(actionListener);
            tglButton.addKeyListener(keyAdapter);
            tglButton.addFocusListener(focusAdapter);
            calendarButtons[i-1] = tglButton;
            tglButton.addKeyListener(new KeyAdapter() {
                @Override
                public void keyPressed(KeyEvent evt) {
                    if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
                        cancelButtonActionPerformed(null);
                    }
                }
            });
        }
        setSize(450, 300);
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents() {
        topPanel = new javax.swing.JPanel();
        monthPanel = new javax.swing.JPanel();
        prevMonthButton = new javax.swing.JButton();
        nextMonthButton = new javax.swing.JButton();
        monthLabel = new javax.swing.JLabel();
        todayButton = new javax.swing.JButton();
        yearPanel = new javax.swing.JPanel();
        prevYearButton = new javax.swing.JButton();
        nextYearButton = new javax.swing.JButton();
        yearButton = new javax.swing.JButton();
        calendarPanel = new javax.swing.JPanel();
        bottomPanel = new javax.swing.JPanel();
        cancelButton = new javax.swing.JButton();

        setTitle("Tanggal");
        setMinimumSize(new java.awt.Dimension(300, 300));
        setModal(true);
        setResizable(false);
        topPanel.setPreferredSize(new java.awt.Dimension(510, 35));
        monthPanel.setLayout(new java.awt.BorderLayout());

        monthPanel.setMinimumSize(new java.awt.Dimension(175, 23));
        monthPanel.setPreferredSize(new java.awt.Dimension(175, 27));
        prevMonthButton.setFont(new java.awt.Font("Lucida Sans", 0, 12));
        prevMonthButton.setText("<");
        prevMonthButton.setMargin(new java.awt.Insets(1, 2, 2, 2));
        prevMonthButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                prevMonthButtonActionPerformed(evt);
            }
        });
        prevMonthButton.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                prevMonthButtonKeyPressed(evt);
            }
        });

        monthPanel.add(prevMonthButton, java.awt.BorderLayout.WEST);

        nextMonthButton.setFont(new java.awt.Font("Lucida Sans", 0, 12));
        nextMonthButton.setText(">");
        nextMonthButton.setMargin(new java.awt.Insets(1, 2, 2, 2));
        nextMonthButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nextMonthButtonActionPerformed(evt);
            }
        });
        nextMonthButton.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                nextMonthButtonKeyPressed(evt);
            }
        });

        monthPanel.add(nextMonthButton, java.awt.BorderLayout.EAST);

        monthLabel.setFont(new java.awt.Font("Lucida Sans", 0, 12));
        monthLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        monthLabel.setText("monthLabel");
        monthPanel.add(monthLabel, java.awt.BorderLayout.CENTER);

        topPanel.add(monthPanel);

        todayButton.setFont(new java.awt.Font("Lucida Sans", 0, 12));
        todayButton.setText("Today");
        todayButton.setMargin(new java.awt.Insets(1, 1, 1, 1));
        todayButton.setPreferredSize(new java.awt.Dimension(83, 25));
        todayButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                todayButtonActionPerformed(evt);
            }
        });
        todayButton.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                todayButtonKeyPressed(evt);
            }
        });

        topPanel.add(todayButton);

        yearPanel.setLayout(new java.awt.BorderLayout());

        yearPanel.setMinimumSize(new java.awt.Dimension(160, 23));
        yearPanel.setPreferredSize(new java.awt.Dimension(160, 27));
        prevYearButton.setFont(new java.awt.Font("Lucida Sans", 0, 12));
        prevYearButton.setText("<");
        prevYearButton.setMargin(new java.awt.Insets(1, 2, 2, 2));
        prevYearButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                prevYearButtonActionPerformed(evt);
            }
        });
        prevYearButton.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                prevYearButtonKeyPressed(evt);
            }
        });

        yearPanel.add(prevYearButton, java.awt.BorderLayout.WEST);

        nextYearButton.setFont(new java.awt.Font("Lucida Sans", 0, 12));
        nextYearButton.setText(">");
        nextYearButton.setMargin(new java.awt.Insets(1, 2, 2, 2));
        nextYearButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nextYearButtonActionPerformed(evt);
            }
        });
        nextYearButton.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                nextYearButtonKeyPressed(evt);
            }
        });

        yearPanel.add(nextYearButton, java.awt.BorderLayout.EAST);

        yearButton.setFont(new java.awt.Font("Lucida Sans", 0, 12));
        yearButton.setText("yearButton");
        yearButton.setMargin(new java.awt.Insets(2, 2, 2, 2));
        yearButton.setPreferredSize(new java.awt.Dimension(100, 23));
        yearButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                yearButtonActionPerformed(evt);
            }
        });
        yearButton.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                yearButtonKeyPressed(evt);
            }
        });

        yearPanel.add(yearButton, java.awt.BorderLayout.CENTER);

        topPanel.add(yearPanel);

        getContentPane().add(topPanel, java.awt.BorderLayout.NORTH);

        calendarPanel.setLayout(new java.awt.GridLayout(7, 7));

        getContentPane().add(calendarPanel, java.awt.BorderLayout.CENTER);

        cancelButton.setFont(new java.awt.Font("Lucida Sans", 0, 12));
        cancelButton.setMnemonic('B');
        cancelButton.setText("Batal");
        cancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelButtonActionPerformed(evt);
            }
        });
        cancelButton.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cancelButtonKeyPressed(evt);
            }
        });

        bottomPanel.add(cancelButton);

        getContentPane().add(bottomPanel, java.awt.BorderLayout.SOUTH);

        pack();
    }// </editor-fold>//GEN-END:initComponents
    
  private void cancelButtonKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cancelButtonKeyPressed
      if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
          cancelButtonActionPerformed(null);
      }
  }//GEN-LAST:event_cancelButtonKeyPressed
  
  private void nextYearButtonKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_nextYearButtonKeyPressed
      if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
          cancelButtonActionPerformed(null);
      }
  }//GEN-LAST:event_nextYearButtonKeyPressed
  
  private void prevYearButtonKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_prevYearButtonKeyPressed
      if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
          cancelButtonActionPerformed(null);
      }
  }//GEN-LAST:event_prevYearButtonKeyPressed
  
  private void todayButtonKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_todayButtonKeyPressed
      if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
          cancelButtonActionPerformed(null);
      }
  }//GEN-LAST:event_todayButtonKeyPressed
  
  private void nextMonthButtonKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_nextMonthButtonKeyPressed
      if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
          cancelButtonActionPerformed(null);
      }
  }//GEN-LAST:event_nextMonthButtonKeyPressed
  
  private void prevMonthButtonKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_prevMonthButtonKeyPressed
      if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
          cancelButtonActionPerformed(null);
      }
  }//GEN-LAST:event_prevMonthButtonKeyPressed
  
  private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelButtonActionPerformed
      proceed = false;
      setVisible(false);
  }//GEN-LAST:event_cancelButtonActionPerformed
  
  private void nextYearButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nextYearButtonActionPerformed
      String[] dateArr = selDate.split("-");
      int day = BspUtility.parseInt(dateArr[2]);
      int month = BspUtility.parseInt(dateArr[1]);
      int year = BspUtility.parseInt(dateArr[0])+1;
      
      int maxDayOfMonth = getMaxDayOfMonth(year, month);
      if (day > maxDayOfMonth) {
          day = maxDayOfMonth;
      }
      
      selDate = year + "-" + month + "-" + day;
      fillCalendar();
  }//GEN-LAST:event_nextYearButtonActionPerformed
  
  private void prevYearButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_prevYearButtonActionPerformed
      String[] dateArr = selDate.split("-");
      int day = BspUtility.parseInt(dateArr[2]);
      int month = BspUtility.parseInt(dateArr[1]);
      int year = BspUtility.parseInt(dateArr[0])-1;
      
      int maxDayOfMonth = getMaxDayOfMonth(year, month);
      if (day > maxDayOfMonth) {
          day = maxDayOfMonth;
      }
      
      selDate = year + "-" + month + "-" + day;
      fillCalendar();
  }//GEN-LAST:event_prevYearButtonActionPerformed
  
  private void todayButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_todayButtonActionPerformed
      selDate = curDate;
      fillCalendar();
  }//GEN-LAST:event_todayButtonActionPerformed
  
  private void nextMonthButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nextMonthButtonActionPerformed
      String[] dateArr = selDate.split("-");
      int day = BspUtility.parseInt(dateArr[2]);
      int month = BspUtility.parseInt(dateArr[1])+1;
      int year = BspUtility.parseInt(dateArr[0]);
      
      if (month > 12) {
          month -= 12;
          year++;
      }
      
      int maxDayOfMonth = getMaxDayOfMonth(year, month);
      if (day > maxDayOfMonth) {
          day = maxDayOfMonth;
      }
      
      selDate = year + "-" + month + "-" + day;
      fillCalendar();
  }//GEN-LAST:event_nextMonthButtonActionPerformed
  
  private void prevMonthButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_prevMonthButtonActionPerformed
      String[] dateArr = selDate.split("-");
      int day = BspUtility.parseInt(dateArr[2]);
      int month = BspUtility.parseInt(dateArr[1])-1;
      int year = BspUtility.parseInt(dateArr[0]);
      
      if (month < 1) {
          month += 12;
          year--;
      }
      
      int maxDayOfMonth = getMaxDayOfMonth(year, month);
      if (day > maxDayOfMonth) {
          day = maxDayOfMonth;
      }
      
      selDate = year + "-" + month + "-" + day;
      fillCalendar();
  }//GEN-LAST:event_prevMonthButtonActionPerformed
  
private void yearButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_yearButtonActionPerformed
    String result = JOptionPane.showInputDialog(this, "Tahun",
            yearButton.getText());
    if (result == null) {
        return;
    }
    String[] dateArr = selDate.split("-");
    int day = BspUtility.parseInt(dateArr[2]);
    int month = BspUtility.parseInt(dateArr[1]);
    int year = BspUtility.parseInt(result);
    
    int maxDayOfMonth = getMaxDayOfMonth(year, month);
    if (day > maxDayOfMonth) {
        day = maxDayOfMonth;
    }
    
    selDate = year + "-" + month + "-" + day;
    fillCalendar();
}//GEN-LAST:event_yearButtonActionPerformed

private void yearButtonKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_yearButtonKeyPressed
    if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
        cancelButtonActionPerformed(null);
    }
}//GEN-LAST:event_yearButtonKeyPressed

public static int compareDateSql(String date1, String date2) {
    // if return value > 0  : date1 > date2
    // if return value == 0 : date1 == date2
    // if return value < 0  : date1 < date2
    
    String[] groups1 = date1.split(" ");
    String[] groups2 = date2.split(" ");
    
    String[] comps1 = groups1[0].split("-");
    String[] comps2 = groups2[0].split("-");
    
    // Compare the years
    int val1 = BspUtility.parseInt(comps1[0]);
    int val2 = BspUtility.parseInt(comps2[0]);
    if (val1 > val2) {
        return 1;
    }
    if (val1 < val2) {
        return -1;
    }
    
    // Compare the months
    val1 = BspUtility.parseInt(comps1[1]);
    val2 = BspUtility.parseInt(comps2[1]);
    if (val1 > val2) {
        return 1;
    }
    if (val1 < val2) {
        return -1;
    }
    
    // Compare the days
    val1 = BspUtility.parseInt(comps1[2]);
    val2 = BspUtility.parseInt(comps2[2]);
    if (val1 > val2) {
        return 1;
    }
    if (val1 < val2) {
        return -1;
    }
    
    // Compare for times
    if (groups1.length > groups2.length) {
        return 1;
    }
    if (groups1.length < groups2.length) {
        return -1;
    }
    
    if (groups1.length > 1) {
        comps1 = groups1[1].split(":");
        comps2 = groups2[1].split(":");
        
        // Compare the hours
        val1 = BspUtility.parseInt(comps1[0]);
        val2 = BspUtility.parseInt(comps2[0]);
        if (val1 > val2) {
            return 1;
        }
        if (val1 < val2) {
            return -1;
        }
        
        // Compare the minutes
        val1 = BspUtility.parseInt(comps1[1]);
        val2 = BspUtility.parseInt(comps2[1]);
        if (val1 > val2) {
            return 1;
        }
        if (val1 < val2) {
            return -1;
        }
        
        if (comps1.length > comps2.length) {
            return 1;
        }
        if (comps1.length < comps2.length) {
            return -1;
        }
        
        if (comps1.length > 2) {
            // Compare the seconds
            val1 = BspUtility.parseInt(comps1[2]);
            val2 = BspUtility.parseInt(comps2[2]);
            if (val1 > val2) {
                return 1;
            }
            if (val1 < val2) {
                return -1;
            }
        }
        
    }
    
    return 0;
}

public static String dateToSQLDate(Date dt) {
    Calendar cal = GregorianCalendar.getInstance();
    cal.setTime(dt);
    return cal.get(Calendar.YEAR) + "-" +
            BspUtility.leadingZero(cal.get(Calendar.MONTH), 2) + "-" +
            BspUtility.leadingZero(cal.get(Calendar.DAY_OF_MONTH), 2);
}

private void fillCalendar() {
    if (focusTimer != null) {
        focusTimer.cancel();
    }
    
    calendarPanel.removeAll();
    
    if (selDate == null) {
        selDate = getCurrentDate();
    }
    if (selDate.equals("")) {
        selDate = getCurrentDate();
    }
    
    String[] dateArr = selDate.split("-");
    int day = BspUtility.parseInt(dateArr[2]);
    int month = BspUtility.parseInt(dateArr[1]);
    int year = BspUtility.parseInt(dateArr[0]);
    
    if (curDate == null) {
        curDate = getCurrentDate();
    }
    if (curDate.equals("")) {
        curDate = getCurrentDate();
    }
    dateArr = curDate.split("-");
    int curDay = BspUtility.parseInt(dateArr[2]);
    int curMonth = BspUtility.parseInt(dateArr[1]);
    int curYear = BspUtility.parseInt(dateArr[0]);
    
    int componenCount = 0;
    int blankCount = 0;
    for (int i=0; i < 7; i++) {
        calendarPanel.add(headerLabels[i]);
        componenCount++;
    }
    
    GregorianCalendar gCal = new GregorianCalendar(year, month-1, 0);
    // GregorianCalendar's month started with 0, not 1
    int blank = gCal.get(Calendar.DAY_OF_WEEK)-1;
    for (int i=0; i < blank; i++) {
        calendarPanel.add(blankLabels[blankCount++]);
        componenCount++;
    }
    
    maxDayCount = getMaxDayOfMonth(year, month);
    Insets ins = new Insets(1, 1, 2, 1);
    for (int i=1; i <= maxDayCount; i++) {
        if (i == curDay && month == curMonth && year == curYear) {
            calendarButtons[i-1].setBackground(Color.yellow);
        } else {
            calendarButtons[i-1].setBackground(Color.white);
        }
        if (i == day && selectedCalendarButton == -1) {
            selectedCalendarButton = i-1;
        }
        calendarPanel.add(calendarButtons[i-1]);
        componenCount++;
    }
    
    while (componenCount < 49) {
        calendarPanel.add(blankLabels[blankCount++]);
        componenCount++;
    }
    
    monthLabel.setText(getMonthName_id(month));
    yearButton.setText(String.valueOf(year));
    
    TimerTask focusTask = new TimerTask() {
        public void run() {
            if (calendarButtons[selectedCalendarButton].isVisible()) {
                calendarButtons[selectedCalendarButton].requestFocusInWindow();
            }
        }
    };
    focusTimer = new Timer();
    focusTimer.schedule(focusTask, 300);
}

public static long getAgeDay(String dt) {
    long age = 0;
    
    if (dt.length() == 0) {
        return age;
    }
    
    GregorianCalendar gc1 = new GregorianCalendar(
            Calendar.getInstance().get(Calendar.YEAR),
            Calendar.getInstance().get(Calendar.MONTH)+1,
            Calendar.getInstance().get(Calendar.DAY_OF_MONTH),
            Calendar.getInstance().get(Calendar.HOUR_OF_DAY),
            Calendar.getInstance().get(Calendar.MINUTE),
            Calendar.getInstance().get(Calendar.SECOND));
    GregorianCalendar gc2 = new GregorianCalendar(
            BspDate.getPartYearSql(dt),
            BspDate.getPartMonthSql(dt),
            BspDate.getPartDaySql(dt),
            Calendar.getInstance().get(Calendar.HOUR_OF_DAY),
            Calendar.getInstance().get(Calendar.MINUTE),
            Calendar.getInstance().get(Calendar.SECOND));
    
    Date d1 = gc1.getTime();
    Date d2 = gc2.getTime();
    
    long l1 = d1.getTime();
    long l2 = d2.getTime();
    
    age = l2 - l1;
    
    return age;
}

public static float getAgeMonthSql(String dt) {
    float age = 0;
    
    if (dt.length() == 0) {
        return age;
    }
    
    int currentYear = Calendar.getInstance().get(Calendar.YEAR);
    int currentMonth = Calendar.getInstance().get(Calendar.MONTH);
    int currentDay = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
    
    String[] bday = dt.split("-");
    if (bday.length == 3) {
        int year = BspUtility.parseInt(bday[0]);
        int month = BspUtility.parseInt(bday[1])-1;
        int day = BspUtility.parseInt(bday[2]);
        
        age = (currentYear - year) * 12;
        age += currentMonth - month;
        
        GregorianCalendar gcal = new GregorianCalendar(currentYear, currentMonth, currentDay);
        age += (currentDay - day) / (float) gcal.getMaximum(Calendar.DAY_OF_MONTH);
    }
    
    return age;
}

public static float getAgeYearSql(String dt) {
    float age = 0;
    
    if (dt.length() == 0) {
        return age;
    }
    
    int currentYear = Calendar.getInstance().get(Calendar.YEAR);
    int currentMonth = Calendar.getInstance().get(Calendar.MONTH);
    
    String[] bday = dt.split("-");
    if (bday.length == 3) {
        int year = BspUtility.parseInt(bday[0]);
        int month = BspUtility.parseInt(bday[1])-1;
        
        age = currentYear - year;
        age += (float) (currentMonth - month) / 12;
    }
    
    return age;
}

public static String getCurrentDate() {
    String temp = BspUtility.leadingZero(String.valueOf(
            Calendar.getInstance().get(
            Calendar.DAY_OF_MONTH)), 2) + "/";
    temp += BspUtility.leadingZero(String.valueOf(
            Calendar.getInstance().get(
            Calendar.MONTH)+1), 2) + "/";
    temp += BspUtility.leadingZero(String.valueOf(
            Calendar.getInstance().get(
            Calendar.YEAR)), 4);
    return temp;
}

public static String getDateEndOfMonthSql() {
    String[] dateArr = getCurrentDate().split("-");
    return (dateArr[0] + "-" + dateArr[1] + "-" +
            getMaxDayOfMonth(BspUtility.parseInt(dateArr[0]),
            BspUtility.parseInt(dateArr[1])));
}

public static String getDateLastMonthSql() {
    String[] dateArr = getCurrentDate().split("-");
    int year = Integer.parseInt(dateArr[0]);
    int mon = Integer.parseInt(dateArr[1]) - 1;
    int day = Integer.parseInt(dateArr[2]);
    
    if (mon < 1) {
        mon += 12;
        year--;
    }
    
    int maxDay = getMaxDayOfMonth(year, mon);
    if (day > maxDay) {
        day = maxDay;
    }
    
    return String.valueOf(year) + "-" + String.valueOf(mon) +
            "-" + String.valueOf(day);
}

public static String getDateStartOfMonthSql() {
    String[] dateArr = getCurrentDate().split("-");
    return (dateArr[0] + "-" + dateArr[1] + "-1");
}

public static String getLongDateString_ID(Date date) {
    String str = null;
    Calendar calendar = (Calendar) GregorianCalendar.getInstance();
    calendar.setTime(date);
    str = String.valueOf(calendar.get(Calendar.DAY_OF_MONTH)) + " ";
    str += monthName_ID[calendar.get(Calendar.MONTH)] + " ";
    str += String.valueOf(calendar.get(Calendar.YEAR));
    return str;
}
    
public static String getDayNameShort(int dayOfWeek) {
    String[] names = { "Min", "Sen", "Sel", "Rab",
    "Kam", "Jum", "Sab" };
    if (dayOfWeek < 7) {
        return names[dayOfWeek];
    }
    return "";
}

public String getDialogDate() {
    return selDate;
}

public static int getDiffDaySql(String dtBegin, String dtEnd) {
    float age = 0;
    
    if (dtBegin == null || dtBegin.length() == 0) {
        return (int) age;
    }
    if (dtEnd == null || dtEnd.length() == 0) {
        return (int) age;
    }
    
    int beginYear, beginMonth, beginDay, endYear, endMonth, endDay;
    
    String[] dtArr = dtBegin.split("-");
    if (dtArr.length == 3) {
        beginYear = BspUtility.parseInt(dtArr[0]);
        beginMonth = BspUtility.parseInt(dtArr[1])-1;
        beginDay = BspUtility.parseInt(dtArr[2]);
    } else {
        return (int) age;
    }
    
    dtArr = dtEnd.split("-");
    if (dtArr.length == 3) {
        endYear = BspUtility.parseInt(dtArr[0]);
        endMonth = BspUtility.parseInt(dtArr[1])-1;
        endDay = BspUtility.parseInt(dtArr[2]);
        
        age = (endYear - beginYear) * (float) 365.25;
        age += (endMonth - beginMonth) * (float) 30.4375;
        age += (endDay - beginDay);
        GregorianCalendar gcal = new GregorianCalendar(endYear, endMonth, endDay);
        if (gcal.getMaximum(Calendar.DAY_OF_MONTH) == 31) {
            age = (float) Math.ceil(age);
        } else {
            age = (float) Math.floor(age);
        }
    }
    
    return (int) age;
}

public static float getDiffMonthSql(String dtBegin, String dtEnd) {
    float age = 0;
    
    if (dtBegin == null || dtBegin.length() == 0) {
        return age;
    }
    if (dtEnd == null || dtEnd.length() == 0) {
        return age;
    }
    
    int beginYear, beginMonth, beginDay, endYear, endMonth, endDay;
    
    String[] dtArr = dtBegin.split("-");
    if (dtArr.length == 3) {
        beginYear = BspUtility.parseInt(dtArr[0]);
        beginMonth = BspUtility.parseInt(dtArr[1])-1;
        beginDay = BspUtility.parseInt(dtArr[2]);
    } else {
        return age;
    }
    
    dtArr = dtEnd.split("-");
    if (dtArr.length == 3) {
        endYear = BspUtility.parseInt(dtArr[0]);
        endMonth = BspUtility.parseInt(dtArr[1])-1;
        endDay = BspUtility.parseInt(dtArr[2]);
        
        age = (endYear - beginYear) * 12;
        age += endMonth - beginMonth;
        
        GregorianCalendar gcal = new GregorianCalendar(endYear, endMonth, endDay);
        age += (endDay - beginDay) / (float) gcal.getMaximum(Calendar.DAY_OF_MONTH);
    }
    
    return age;
}

public static int getMaxDayOfMonth(int year, int month) {
    int[] dayNum = { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };
    int dMonth = 0;
    if ((year > 0) &&
            (year < 10000)) {
        if (month > 0 && month < 13) {
            dMonth = dayNum[month-1];
            GregorianCalendar gCal = new GregorianCalendar(year, month, 1);
            if (month ==2 && gCal.isLeapYear(year)) {
                dMonth++;
            }
        }
    } //- if
    return dMonth;
}

public static String getMonthName_id(int month) {
    String[] names = { "Januari", "Pebruari", "Maret", "April",
    "Mei", "Juni", "Juli", "Agustus",
    "September", "Oktober", "Nopember", "Desember" };
    if (month > 0 && month < 13) {
        return names[month-1];
    }
    return "";
}

public static int getPartDaySql(String dt) {
    int day = 0;
    
    if (dt.length() > 0) {
        String[] dtArr1 = dt.split(" ");
        if (dtArr1.length > 0) {
            String[] dtArr2 = dt.split("-");
            if (dtArr2.length > 2) {
                day = BspUtility.parseInt(dtArr2[2]);
            }
        }
    }
    
    return day;
}

public static int getPartMonthSql(String dt) {
    int mon = 0;
    
    if (dt.length() > 0) {
        String[] dtArr1 = dt.split(" ");
        if (dtArr1.length > 0) {
            String[] dtArr2 = dt.split("-");
            if (dtArr2.length > 1) {
                mon = BspUtility.parseInt(dtArr2[1]);
            }
        }
    }
    
    return mon;
}

public static int getPartYearSql(String dt) {
    int year = 0;
    
    if (dt.length() > 0) {
        String[] dtArr1 = dt.split(" ");
        if (dtArr1.length > 0) {
            String[] dtArr2 = dt.split("-");
            if (dtArr2.length > 0) {
                year = BspUtility.parseInt(dtArr2[0]);
            }
        }
    }
    
    return year;
}

public static String getTimeLong() {
    StringBuffer temp = new StringBuffer();
    temp.append(BspUtility.leadingZero(String.valueOf(
            Calendar.getInstance().get(
            Calendar.HOUR_OF_DAY)), 2) + ":");
    temp.append(BspUtility.leadingZero(String.valueOf(
            Calendar.getInstance().get(
            Calendar.MINUTE)), 2) + ":");
    temp.append(BspUtility.leadingZero(String.valueOf(
            Calendar.getInstance().get(
            Calendar.SECOND)), 2));
    return temp.toString();
}

public static String getTimeShort() {
    StringBuffer temp = new StringBuffer();
    temp.append(BspUtility.leadingZero(String.valueOf(
            Calendar.getInstance().get(
            Calendar.HOUR_OF_DAY)), 2) + ":");
    temp.append(BspUtility.leadingZero(String.valueOf(
            Calendar.getInstance().get(
            Calendar.MINUTE)), 2));
    return temp.toString();
}

public static boolean isDateValidSql(String dt) {
    if (dt.trim().length() == 0) {
        return true;
    }
    String[] dtArr_1 = dt.trim().split("-");
    if (dtArr_1.length != 3) {
        return false;
    }
    int val = BspUtility.parseInt(dtArr_1[0]);
    if (val == 0) {
        return false;
    }
    val = BspUtility.parseInt(dtArr_1[1]);
    if (val == 0) {
        return false;
    }
    val = BspUtility.parseInt(dtArr_1[2]);
    if (val == 0) {
        return false;
    }
    return true;
}

public boolean showDateDialog(String selectedDate, String currentDate,
        Point location) {
    selDate = selectedDate;
    curDate = currentDate;
    
    if (location == null) {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension frameSize = getSize();
        location = new Point();
        location.x = screenSize.width / 2 - frameSize.width / 2;
        location.y = screenSize.height / 2 - frameSize.height / 2 - 30;
    } else {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension frameSize = getSize();
        if (location.y + frameSize.height > screenSize.height) {
            location.y = (screenSize.height-frameSize.height-30);
        }
        if (location.x + frameSize.width > screenSize.width) {
            location.x = (screenSize.width-frameSize.width-5);
        }
    }
    
    fillCalendar();
    setLocation(location);
    proceed = false;
    setVisible(true);
    return proceed;
}

public boolean showDateDialog(String selectedDate, String currentDate,
        JTextField dateField, Point location) {
    selDate = selectedDate;
    curDate = currentDate;
    this.dateField = dateField;
    
    if (location == null) {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension frameSize = getSize();
        location = new Point();
        location.x = screenSize.width / 2 - frameSize.width / 2;
        location.y = screenSize.height / 2 - frameSize.height / 2 - 30;
    } else {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension frameSize = getSize();
        if (location.y + frameSize.height > screenSize.height) {
            location.y = (screenSize.height-frameSize.height-30);
        }
        if (location.x + frameSize.width > screenSize.width) {
            location.x = (screenSize.width-frameSize.width-5);
        }
    }
    
    fillCalendar();
    setLocation(location);
    proceed = false;
    setVisible(true);
    return proceed;
}

public boolean showDateDialog(String selectedDate, String currentDate,
        JLabel dateLabel, Point location) {
    selDate = selectedDate;
    curDate = currentDate;
    this.dateLabel = dateLabel;
    
    if (location == null) {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension frameSize = getSize();
        location = new Point();
        location.x = screenSize.width / 2 - frameSize.width / 2;
        location.y = screenSize.height / 2 - frameSize.height / 2 - 30;
    } else {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension frameSize = getSize();
        if (location.y + frameSize.height > screenSize.height) {
            location.y = (screenSize.height-frameSize.height-30);
        }
        if (location.x + frameSize.width > screenSize.width) {
            location.x = (screenSize.width-frameSize.width-5);
        }
    }
    
    fillCalendar();
    setLocation(location);
    proceed = false;
    setVisible(true);
    return proceed;
}

public Date SQLDateToDate(String sqlDate) {
    int year = getPartYearSql(sqlDate);
    int month = getPartMonthSql(sqlDate);
    int day = getPartDaySql(sqlDate);
    
    Calendar cal = GregorianCalendar.getInstance();
    cal.set(year, month, day);
    return cal.getTime();
}

public static String swapDate(String dt) {
    String temp = "";
    if (dt.equals("0001-01-01") || dt.equals("01/01/0001")) {
        return temp;
    }
    
    String[] dtArr_1 = dt.trim().split(" ");
    
    if (dtArr_1.length > 1) {
        if (dtArr_1[0].indexOf("-") == -1 && dtArr_1[0].indexOf("/") == -1) {
            temp = dtArr_1[0];
            dtArr_1[0] = dtArr_1[1];
            dtArr_1[1] = temp;
        }
    }
    
    temp = "";
    if (dtArr_1.length > 0) {
        if (dtArr_1[0].indexOf("-") != -1) {
            String[] dtArr_2 = dtArr_1[0].split("-");
            if (dtArr_2.length > 2) {
                temp = dtArr_2[2] + "/" + dtArr_2[1] + "/" + dtArr_2[0];
            }
        } else if (dtArr_1[0].indexOf("/") != -1) {
            String[] dtArr_2 = dtArr_1[0].split("/");
            if (dtArr_2.length > 2) {
                temp = dtArr_2[2] + "-" + dtArr_2[1] + "-" + dtArr_2[0];
            }
        }
    }
    
    if (dtArr_1.length > 1) {
        temp += " " + dtArr_1[1];
    }
    
    return temp;
}


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel bottomPanel;
    private javax.swing.JPanel calendarPanel;
    private javax.swing.JButton cancelButton;
    private javax.swing.JLabel monthLabel;
    private javax.swing.JPanel monthPanel;
    private javax.swing.JButton nextMonthButton;
    private javax.swing.JButton nextYearButton;
    private javax.swing.JButton prevMonthButton;
    private javax.swing.JButton prevYearButton;
    private javax.swing.JButton todayButton;
    private javax.swing.JPanel topPanel;
    private javax.swing.JButton yearButton;
    private javax.swing.JPanel yearPanel;
    // End of variables declaration//GEN-END:variables
    
}
