/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sqlitemanager;

import java.sql.SQLException;
import java.util.Vector;

import javax.swing.JOptionPane;

/**
 *
 * @author ChaseLP
 */
@SuppressWarnings("serial")
public class QueryBuilder extends javax.swing.JFrame {
	//reference to the sql connection
	private DBConnection dbserv;
	
	//setter for MainWindow, the creator of this window
    public void setdbserv(DBConnection _dbserv) {
    	this.dbserv = _dbserv;
    	this.allcols = this.dbserv.getcolumns();
    	this.alltables = this.dbserv.gettables();
    	this.tablename = this.alltables.firstElement();
    	String tempstr[] = new String[alltables.size()];
    	int i = 0;
    	for (String s : alltables) {
    		tempstr[i] = s;
    		++i;
    	}
    	this.TableComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(tempstr));
    	tempstr = new String[allcols.size()];
    	i = 0;
    	for (String s : allcols) {
    		tempstr[i] = s;
    		++i;
    	}
    	this.ActiveColumnComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(tempstr));    	
    	this.SearchColumnComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(tempstr));
    	this.SortColumnComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(tempstr));
    	this.ActiveDBFileLabel.setText("File: " + this.dbserv.getDBPath());
    	allcols = new Vector<String>();
    	alltables = new Vector<String>();
    	activecols = new Vector<String>();
    	criterion = new Vector<String>();
    }
    
    private Vector<String> allcols;
    private Vector<String> alltables;
    private Vector<String> activecols;
    private Vector<String> criterion;
    private String tablename;
    private boolean haverestrictions;
    private boolean sortascending;
    private int resultlimit;
    private void ResetButtonActionPerformed(java.awt.event.ActionEvent evt) {                                            
        activecols.clear();
        criterion.clear();
    	this.CurrentActiveColsComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "" }));
    	this.CurrentFiltersComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "" }));
    	this.SearchLimitTextField.setText("20");
    	this.DoSortCheckBox.setSelected(false);
    	this.SearchTextField.setText("value");
    	if (this.AscendingButton.getText() != "Ascending") {
            this.AscendingButton.setText("Ascending");
            sortascending = true;
    	}
    }                                           

    private void AddActiveColButtonActionPerformed(java.awt.event.ActionEvent evt) {                                                   
        String addme = String.valueOf(this.ActiveColumnComboBox.getSelectedItem());
        if (this.activecols.size() > 0 && this.activecols.contains(addme)) return;
        this.activecols.add(addme);
    	String tempstr[] = new String[this.activecols.size()];
    	int i = 0;
    	for (String s : this.activecols) {
    		tempstr[i] = s;
    		++i;
    	}
    	this.CurrentActiveColsComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(tempstr));  
    }                                                  

    private void AddSearchButtonActionPerformed(java.awt.event.ActionEvent evt) {
    	String newquery = "";
    	if (this.criterion.size() > 0) newquery += " and ";
    	newquery += String.valueOf(this.SearchColumnComboBox.getSelectedItem());
    	newquery += " " + String.valueOf(this.SearchOperationComboBox.getSelectedItem());
    	newquery += " " + 	this.SearchTextField.getText();
    	if (this.criterion != null && this.criterion.contains(newquery)) return;
    	int confirmation = JOptionPane.showConfirmDialog (null, newquery,"Confirm",JOptionPane.YES_NO_OPTION);
    	if (confirmation == 0) {
			this.criterion.add(newquery);
        	String tempstr[] = new String[this.criterion.size()];
        	int i = 0;
        	for (String s : this.criterion) {
        		tempstr[i] = s;
        		++i;
        	}
        	this.CurrentFiltersComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(tempstr));  
    	}
    }                                               

    private void AscendingButtonActionPerformed(java.awt.event.ActionEvent evt) {
    	if (this.AscendingButton.getText() == "Ascending")
            this.AscendingButton.setText("Descending");
        else
            this.AscendingButton.setText("Ascending");
        sortascending = !sortascending;
    }                                               

    private void ExitButtonActionPerformed(java.awt.event.ActionEvent evt) {                                           
    	this.dispose();
    }                                          

    private void RunQueryButtonActionPerformed(java.awt.event.ActionEvent evt) {                                               
        try {
        	boolean ranonce = false;
        	String querystring = "select ";
        	if (activecols.size() > 0) {
	        	for (String s : activecols) {
	        		if (ranonce) querystring += ", ";
	        		else ranonce = true;
	        		querystring += s;
	        	}
        	}
        	else querystring += "*";
        	querystring += " from " + tablename;
        	if (haverestrictions && criterion != null) {
        		querystring += " where ";
        		ranonce = false;
        		for (String s : criterion) {
        			querystring += s;
        		}
        	}
        	if (this.DoSortCheckBox.isSelected()) {
        		querystring += " order by " + String.valueOf(this.SortColumnComboBox.getSelectedItem());
        		if (sortascending)
        			querystring += " asc";
        		else querystring += " desc";
        	}
        	try { this.resultlimit = Integer.parseInt(this.SearchLimitTextField.getText()); }
        	catch(java.lang.NumberFormatException e) { this.resultlimit = 0; this.SearchLimitTextField.setText("0"); }
        	if (this.resultlimit > 1)
        		querystring += " limit " + String.valueOf(resultlimit - 1);
        	//this.dbserv.runQuery("select model_year,model_make_display, model_name, model_trim, model_body, model_engine_type, model_engine_cyl, model_engine_cc, model_engine_bore_mm, model_engine_stroke_mm, model_engine_compression, model_engine_power_ps, model_engine_power_rpm, model_engine_torque_nm, model_engine_torque_rpm, model_transmission_type, model_drive, model_weight_kg from cqa where model_make_id = 'ford' and model_engine_cc not NULL and model_name != 'Anglia' and model_name != 'Fiesta' order by model_engine_cc desc limit 15");
        	this.dbserv.runQuery(querystring);
        	ResultWindow theresults = new ResultWindow();
			theresults.addCols(activecols);
			String newrow[] = new String[activecols.size()+1];
			int i;
			do {
				newrow[0] = String.valueOf(this.dbserv.rs.getRow() + 1); //row count
				i = 1;
				for (String s : activecols) {
					newrow[i] = this.dbserv.rs.getString(s);
					++i;
				}
				theresults.addRow(newrow);
				//System.out.print(this.dbserv.rs.getString("model_make_display") + "\n");
			}while (this.dbserv.rs.next());
            theresults.setVisible(true);
		} catch (SQLException e) { System.out.print("except in runquery act\n" + e.getMessage()); }
    }                                              

    private void RemoveColumnButtonActionPerformed(java.awt.event.ActionEvent evt) {
        this.CurrentActiveColsComboBox.removeItemAt(this.CurrentActiveColsComboBox.getSelectedIndex());
    }                                                  

    private void RemoveFilterButtonActionPerformed(java.awt.event.ActionEvent evt) {                                                   
        this.CurrentFiltersComboBox.removeItemAt(this.CurrentFiltersComboBox.getSelectedIndex());
    }
    public QueryBuilder() {
        initComponents();
        sortascending = true;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {

        QueryBuilderTitleLabel = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        ColumnsLabel = new javax.swing.JLabel();
        ActiveColumnComboBox = new javax.swing.JComboBox<>();
        AddActiveColButton = new javax.swing.JButton();
        SearchCriteriaLabel = new javax.swing.JLabel();
        SearchColumnComboBox = new javax.swing.JComboBox<>();
        SearchOperationComboBox = new javax.swing.JComboBox<>();
        SearchTextField = new javax.swing.JTextField();
        jSeparator2 = new javax.swing.JSeparator();
        AddSearchButton = new javax.swing.JButton();
        jSeparator3 = new javax.swing.JSeparator();
        DoSortCheckBox = new javax.swing.JCheckBox();
        SortColumnComboBox = new javax.swing.JComboBox<>();
        AscendingButton = new javax.swing.JButton();
        jSeparator4 = new javax.swing.JSeparator();
        LimitSearchPromptLabel = new javax.swing.JLabel();
        SearchLimitTextField = new javax.swing.JTextField();
        jSeparator5 = new javax.swing.JSeparator();
        ReviewLabel = new javax.swing.JLabel();
        CurrentActiveColsComboBox = new javax.swing.JComboBox<>();
        SelectedColumnsLabel = new javax.swing.JLabel();
        FiltersLabel = new javax.swing.JLabel();
        CurrentFiltersComboBox = new javax.swing.JComboBox<>();
        RunQueryButton = new javax.swing.JButton();
        ResetButton = new javax.swing.JButton();
        ExitButton = new javax.swing.JButton();
        jSeparator6 = new javax.swing.JSeparator();
        ActiveDBFileLabel = new javax.swing.JLabel();
        TableLabel = new javax.swing.JLabel();
        TableComboBox = new javax.swing.JComboBox<>();
        jSeparator7 = new javax.swing.JSeparator();
        RemoveColumnButton = new javax.swing.JButton();
        RemoveFilterButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        QueryBuilderTitleLabel.setFont(new java.awt.Font("Serif", 1, 12)); // NOI18N
        QueryBuilderTitleLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        QueryBuilderTitleLabel.setText("SQLite Database Query Tool");

        ColumnsLabel.setText("Columns to display:");

        ActiveColumnComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "" }));

        AddActiveColButton.setText("Add column");
        AddActiveColButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AddActiveColButtonActionPerformed(evt);
            }
        });

        SearchCriteriaLabel.setText("Search criteria:");

        SearchColumnComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "" }));

        SearchOperationComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "=", "!=", "<", ">", "<=", ">=" }));

        SearchTextField.setText("value");

        AddSearchButton.setText("Add filter");
        AddSearchButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AddSearchButtonActionPerformed(evt);
            }
        });

        DoSortCheckBox.setText("Sort results on");

        SortColumnComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "" }));

        AscendingButton.setText("Ascending");
        AscendingButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AscendingButtonActionPerformed(evt);
            }
        });

        LimitSearchPromptLabel.setText("Maximum number of results to display, or 0 for no limit:");

        SearchLimitTextField.setText("20");

        ReviewLabel.setText("Review:");

        CurrentActiveColsComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "" }));

        SelectedColumnsLabel.setText("Selected Columns");

        FiltersLabel.setText("Filters:");

        CurrentFiltersComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "" }));

        RunQueryButton.setText("Run Query");
        RunQueryButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RunQueryButtonActionPerformed(evt);
            }
        });

        ResetButton.setText("Reset");
        ResetButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ResetButtonActionPerformed(evt);
            }
        });

        ExitButton.setText("Exit");
        ExitButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ExitButtonActionPerformed(evt);
            }
        });

        ActiveDBFileLabel.setText("File: ");

        TableLabel.setText("Table:");

        TableComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "" }));

        RemoveColumnButton.setText("Remove");
        RemoveColumnButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RemoveColumnButtonActionPerformed(evt);
            }
        });

        RemoveFilterButton.setText("Remove");
        RemoveFilterButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RemoveFilterButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(QueryBuilderTitleLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jSeparator1)
            .addComponent(jSeparator7)
            .addComponent(jSeparator2)
            .addComponent(jSeparator3)
            .addComponent(jSeparator4)
            .addComponent(jSeparator5)
            .addComponent(jSeparator6)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(ActiveDBFileLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(ResetButton)
                            .addComponent(ExitButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(RunQueryButton, javax.swing.GroupLayout.PREFERRED_SIZE, 233, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(LimitSearchPromptLabel)
                        .addGap(44, 44, 44)
                        .addComponent(SearchLimitTextField))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(FiltersLabel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(CurrentFiltersComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(TableLabel)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(TableComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 223, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(ColumnsLabel)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(ActiveColumnComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(SearchCriteriaLabel)
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(DoSortCheckBox)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(SortColumnComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(SearchColumnComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(SearchOperationComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(SearchTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(ReviewLabel)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(SelectedColumnsLabel)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(CurrentActiveColsComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 27, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(AddActiveColButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 95, Short.MAX_VALUE)
                            .addComponent(AscendingButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(AddSearchButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(RemoveColumnButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(RemoveFilterButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(QueryBuilderTitleLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(ActiveDBFileLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(TableLabel)
                    .addComponent(TableComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator7, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ColumnsLabel)
                    .addComponent(ActiveColumnComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(AddActiveColButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(SearchCriteriaLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(SearchColumnComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(SearchOperationComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(SearchTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(AddSearchButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(DoSortCheckBox)
                    .addComponent(SortColumnComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(AscendingButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(LimitSearchPromptLabel)
                    .addComponent(SearchLimitTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator5, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ReviewLabel)
                    .addComponent(SelectedColumnsLabel)
                    .addComponent(CurrentActiveColsComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(RemoveColumnButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(RemoveFilterButton)
                    .addComponent(CurrentFiltersComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(FiltersLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator6, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(ResetButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(ExitButton))
                    .addComponent(RunQueryButton, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(QueryBuilder.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(QueryBuilder.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(QueryBuilder.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(QueryBuilder.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new QueryBuilder().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify                     
    private javax.swing.JComboBox<String> ActiveColumnComboBox;
    private javax.swing.JLabel ActiveDBFileLabel;
    private javax.swing.JButton AddActiveColButton;
    private javax.swing.JButton AddSearchButton;
    private javax.swing.JButton AscendingButton;
    private javax.swing.JLabel ColumnsLabel;
    private javax.swing.JComboBox<String> CurrentActiveColsComboBox;
    private javax.swing.JComboBox<String> CurrentFiltersComboBox;
    private javax.swing.JCheckBox DoSortCheckBox;
    private javax.swing.JButton ExitButton;
    private javax.swing.JLabel FiltersLabel;
    private javax.swing.JLabel LimitSearchPromptLabel;
    private javax.swing.JLabel QueryBuilderTitleLabel;
    private javax.swing.JButton RemoveColumnButton;
    private javax.swing.JButton RemoveFilterButton;
    private javax.swing.JButton ResetButton;
    private javax.swing.JLabel ReviewLabel;
    private javax.swing.JButton RunQueryButton;
    private javax.swing.JComboBox<String> SearchColumnComboBox;
    private javax.swing.JLabel SearchCriteriaLabel;
    private javax.swing.JTextField SearchLimitTextField;
    private javax.swing.JComboBox<String> SearchOperationComboBox;
    private javax.swing.JTextField SearchTextField;
    private javax.swing.JLabel SelectedColumnsLabel;
    private javax.swing.JComboBox<String> SortColumnComboBox;
    private javax.swing.JComboBox<String> TableComboBox;
    private javax.swing.JLabel TableLabel;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JSeparator jSeparator7;
    // End of variables declaration                   
}
