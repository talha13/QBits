/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package qbits.gui.employee;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Vector;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.SwingWorker;
import qbits.configuration.Utilities;
import qbits.db.MySQLDatabase;
import qbits.db.QueryBuilder;
import qbits.entity.Employee;
import qbits.gui.common.UIParentFrame;
import qbitserp.common.Message;

/**
 *
 * @author Topu
 */
public class UIEmployee extends javax.swing.JPanel {

    private UIParentFrame parentFrame;
    private boolean isUpdate;
    private Employee employee;
    private HashMap<Integer, Integer> departments;
    private HashMap<Integer, Integer> designations;

    /**
     * Creates new form Sample
     */
    public UIEmployee(UIParentFrame frame) {
        initComponents();
        parentFrame = frame;
        departments = new HashMap<>();
        designations = new HashMap<>();
        reset();
    }

    public void update(int employeeID) {

        isUpdate = true;
        employee = new Employee();
        employee.setCustomerID(employeeID);

        new SwingWorker<Object, Object>() {
            @Override
            protected Object doInBackground() throws Exception {
                parentFrame.stausBar.startLoading("getting employee information");
                return load();
            }

            protected void done() {
                try {
                    parentFrame.stausBar.stopLoading();

                    if (get() == 1) {
                    } else if (get() == -1) {
                        reset();
                        parentFrame.showMessage("Unable to load employee information");

                    } else if (get() == -2) {
                        Message.dbConnectFailed();
                    }
                } catch (InterruptedException ex) {
                    Logger.getLogger(UIEmployee.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ExecutionException ex) {
                    Logger.getLogger(UIEmployee.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        }.execute();
    }

    public int load() {

        MySQLDatabase database = new MySQLDatabase();
        String query;
        int status = -1;

        if (database.connect()) {
            try {

                query = "SELECT employee.employee_id,"
                        + " person.person_id, person.first_name, person.last_name, person.gender, person.date_of_birth, person.contact_no,"
                        + " address.address_id, address.address, address.city, address.district,"
                        + " employee.salary_id, employee_salary.basic, employee_salary.house_rent_allowance, employee_salary.medical_allowance, employee_salary.conveyance_allowance, employee_salary.income_tax, employee_salary.provident_fund"
                        + " FROM employee"
                        + " INNER JOIN person ON person.person_id = employee.person_id"
                        + " INNER JOIN address ON address.address_id = person.address_id"
                        + " INNER JOIN employee_salary ON employee_salary.employee_salary_id = employee.salary_id"
                        + " WHERE employee.employee_id = " + employee.getCustomerID();

                ResultSet resultSet = database.get(query);

                if (resultSet.next()) {

                    txfCity.setText(resultSet.getString("address.city"));
                    txfDistrict.setText(resultSet.getString("address.district"));
                    txfFirstName.setText(resultSet.getString("person.first_name"));
                    txfLastName.setText(resultSet.getString("person.last_name"));
                    txfPhone.setText(resultSet.getString("person.contact_no"));
                    taAddress.setText(resultSet.getString("address.address"));
                    cmbGender.setSelectedIndex(resultSet.getString("person.gender").compareTo("Male") == 0 ? 1 : 2);
                    dcDOB.setSelectedDate(Utilities.getDateChosserDate(resultSet.getDate("person.date_of_birth")));

                    spBasic.setValue(resultSet.getDouble("employee_salary.basic"));
                    spConveyance.setValue(resultSet.getDouble("employee_salary.conveyance_allowance"));
                    spHouseRent.setValue(resultSet.getDouble("employee_salary.house_rent_allowance"));
                    spIncomeTax.setValue(resultSet.getDouble("employee_salary.income_tax"));
                    spMedical.setValue(resultSet.getDouble("employee_salary.medical_allowance"));
                    spProvidentFund.setValue(resultSet.getDouble("employee_salary.provident_fund"));

                    employee.setAddressID(resultSet.getInt("address.address_id"));
                    employee.setPersonID(resultSet.getInt("person.person_id"));
                    employee.getSalary().setSalaryID(resultSet.getInt("employee.salary_id"));

                    status = 1;
                } else {
                    status = -1;
                }

            } catch (SQLException ex) {
                Logger.getLogger(UIEmployee.class.getName()).log(Level.SEVERE, null, ex);
                status = -1;
            } finally {
                database.disconnect();
            }

        } else {
            status = -2;
        }

        return status;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTextField3 = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        taAddress = new javax.swing.JTextArea();
        txfDistrict = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txfCity = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txfPhone = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        dcJoiningDate = new datechooser.beans.DateChooserCombo();
        jLabel19 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        btnSave = new javax.swing.JButton();
        btnReset = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        txfFirstName = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txfLastName = new javax.swing.JTextField();
        cmbGender = new javax.swing.JComboBox();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        dcDOB = new datechooser.beans.DateChooserCombo();
        cmbDepartment = new javax.swing.JComboBox();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        cmbDesgination = new javax.swing.JComboBox();
        jPanel4 = new javax.swing.JPanel();
        spBasic = new javax.swing.JSpinner();
        jLabel10 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        spHouseRent = new javax.swing.JSpinner();
        jLabel13 = new javax.swing.JLabel();
        spMedical = new javax.swing.JSpinner();
        spConveyance = new javax.swing.JSpinner();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        spIncomeTax = new javax.swing.JSpinner();
        jLabel16 = new javax.swing.JLabel();
        spProvidentFund = new javax.swing.JSpinner();

        jTextField3.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jTextField3.setText("jTextField1");

        jLabel2.setText("jLabel2");

        setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(153, 153, 255), 1, true), "Employee", 2, 0, new java.awt.Font("Times New Roman", 0, 12), new java.awt.Color(0, 0, 102))); // NOI18N

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(153, 153, 255), 1, true), "Contact Info"));

        taAddress.setColumns(20);
        taAddress.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        taAddress.setRows(5);
        jScrollPane1.setViewportView(taAddress);

        txfDistrict.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N

        jLabel5.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel5.setText("District");

        txfCity.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N

        jLabel4.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel4.setText("City");

        jLabel3.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel3.setText("Address");

        txfPhone.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N

        jLabel11.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(102, 0, 0));
        jLabel11.setText("Phone*");

        dcJoiningDate.setFieldFont(new java.awt.Font("Times New Roman", java.awt.Font.PLAIN, 14));

        jLabel19.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel19.setText("Joining Date");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5)
                    .addComponent(jLabel11))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(txfPhone, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txfDistrict, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txfCity, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(22, Short.MAX_VALUE))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jLabel19)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(dcJoiningDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txfCity, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txfDistrict, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txfPhone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel19)
                    .addComponent(dcJoiningDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 11, Short.MAX_VALUE))
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(153, 153, 255), 1, true)));

        btnSave.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        btnSave.setIcon(new javax.swing.ImageIcon(getClass().getResource("/qbits/resources/image/Save-icon.png"))); // NOI18N
        btnSave.setText("Save");
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });

        btnReset.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        btnReset.setIcon(new javax.swing.ImageIcon(getClass().getResource("/qbits/resources/image/Refresh-icon.png"))); // NOI18N
        btnReset.setText("Reset");
        btnReset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnResetActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(230, 230, 230)
                .addComponent(btnSave, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnReset)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSave)
                    .addComponent(btnReset))
                .addContainerGap())
        );

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(153, 153, 255), 1, true), "Basic Info"));

        txfFirstName.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N

        jLabel6.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(102, 0, 0));
        jLabel6.setText("First Name*");

        jLabel7.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(102, 0, 0));
        jLabel7.setText("Last Name*");

        txfLastName.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N

        cmbGender.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        cmbGender.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Select Gender", "Male", "Female" }));

        jLabel8.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel8.setText("Gender");

        jLabel9.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel9.setText("Date of Birth");

        dcDOB.setFieldFont(new java.awt.Font("Times New Roman", java.awt.Font.PLAIN, 14));

        cmbDepartment.setEditable(true);
        cmbDepartment.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N

        jLabel17.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel17.setText("Department");

        jLabel18.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel18.setText("Designation");

        cmbDesgination.setEditable(true);
        cmbDesgination.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel6)
                            .addComponent(jLabel7)
                            .addComponent(jLabel8)
                            .addComponent(jLabel9))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(txfLastName)
                                    .addComponent(txfFirstName))
                                .addGap(35, 35, 35))
                            .addComponent(dcDOB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cmbGender, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(36, 36, 36)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel18)
                            .addComponent(jLabel17))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cmbDesgination, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(cmbDepartment, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(92, 92, 92)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txfFirstName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txfLastName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel8)
                    .addComponent(cmbGender, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel9)
                    .addComponent(dcDOB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel17)
                    .addComponent(cmbDepartment, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmbDesgination, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel18))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(153, 153, 255), 1, true), "Salary"));

        spBasic.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        spBasic.setModel(new javax.swing.SpinnerNumberModel(Double.valueOf(0.0d), null, null, Double.valueOf(1.0d)));

        jLabel10.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(102, 0, 0));
        jLabel10.setText("Basic");

        jLabel12.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(102, 0, 0));
        jLabel12.setText("House Rent");

        spHouseRent.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        spHouseRent.setModel(new javax.swing.SpinnerNumberModel(Double.valueOf(0.0d), null, null, Double.valueOf(1.0d)));

        jLabel13.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(102, 0, 0));
        jLabel13.setText("Medical");

        spMedical.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        spMedical.setModel(new javax.swing.SpinnerNumberModel(Double.valueOf(0.0d), null, null, Double.valueOf(1.0d)));

        spConveyance.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        spConveyance.setModel(new javax.swing.SpinnerNumberModel(Double.valueOf(0.0d), null, null, Double.valueOf(1.0d)));

        jLabel14.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(102, 0, 0));
        jLabel14.setText("Conveyance");

        jLabel15.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(102, 0, 0));
        jLabel15.setText("IncomeTax");

        spIncomeTax.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        spIncomeTax.setModel(new javax.swing.SpinnerNumberModel(0.0d, 0.0d, 100.0d, 1.0d));

        jLabel16.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(102, 0, 0));
        jLabel16.setText("ProvidentFund");

        spProvidentFund.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        spProvidentFund.setModel(new javax.swing.SpinnerNumberModel(0.0d, 0.0d, 100.0d, 1.0d));

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(spBasic, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel12)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(spHouseRent, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel13)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(spMedical, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(50, 50, 50))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel14)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(spConveyance, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel15)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(spIncomeTax, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel16)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(spProvidentFund, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(32, 32, 32))))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(spBasic, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12)
                    .addComponent(spHouseRent, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13)
                    .addComponent(spMedical, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(spConveyance, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel15)
                    .addComponent(spIncomeTax, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel16)
                    .addComponent(spProvidentFund, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 342, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 207, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        // TODO add your handling code here:
        if (!check()) {
            return;
        }

        new SwingWorker<Object, Object>() {
            @Override
            protected Object doInBackground() throws Exception {
                parentFrame.stausBar.startLoading("saving employee");
                changeStatus(false);

                if (isUpdate) {
                    return update();
                } else {
                    return save();
                }
            }

            protected void done() {
                try {
                    parentFrame.stausBar.stopLoading();
                    changeStatus(true);
                    if (get() == 1) {
                        reset();
                        parentFrame.showMessage("Employee information saved");
                    } else if (get() == -1) {
                        parentFrame.showMessage("Unable to save Employee info");
                    }
                } catch (InterruptedException ex) {
                    Logger.getLogger(UIEmployee.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ExecutionException ex) {
                    Logger.getLogger(UIEmployee.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }.execute();

    }//GEN-LAST:event_btnSaveActionPerformed

    private void btnResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResetActionPerformed
        // TODO add your handling code here:
        reset();
    }//GEN-LAST:event_btnResetActionPerformed

    private void changeStatus(boolean status) {

        txfCity.setEnabled(status);
        txfDistrict.setEnabled(status);
        txfFirstName.setEnabled(status);
        txfLastName.setEnabled(status);
        txfPhone.setEnabled(status);
        cmbGender.setEnabled(status);
        dcDOB.setEnabled(status);
        btnReset.setEnabled(status);
        btnSave.setEnabled(status);
        taAddress.setEnabled(status);
    }

    private void reset() {

        spBasic.setValue(0);
        spConveyance.setValue(0);
        spHouseRent.setValue(0);
        spIncomeTax.setValue(0);
        spMedical.setValue(0);
        spProvidentFund.setValue(0);
        taAddress.setText("");
        txfCity.setText("");
        txfDistrict.setText("");
        txfFirstName.setText("");
        txfLastName.setText("");
        txfPhone.setText("");
        cmbGender.setSelectedIndex(0);
        dcDOB.setSelectedDate(Calendar.getInstance());
        dcJoiningDate.setSelectedDate(Calendar.getInstance());
        isUpdate = false;
        loadDepartment();
        loadDesignation();
    }

    private int update() {

        MySQLDatabase database = new MySQLDatabase();
        String query;
        int status = -1;
        QueryBuilder queryBuilder = new QueryBuilder();

        if (database.connect()) {

            database.setAutoCommit(false);

            query = "UPDATE person SET first_name = \"" + txfFirstName.getText() + "\", last_name = \"" + txfLastName.getText() + "\""
                    + ", gender = \"" + cmbGender.getSelectedItem().toString() + "\", date_of_birth = \"" + Utilities.dateForDB(dcDOB.getSelectedDate().getTime()) + "\""
                    + ", contact_no = \"" + txfPhone.getText() + "\""
                    + " WHERE person_id = " + employee.getPersonID();

            long affactedRows = database.update(query);

            if (affactedRows <= 0) {
                database.rollback();
                database.setAutoCommit(true);
                database.disconnect();
                return -1;
            }

            query = "UPDATE address SET address = \"" + taAddress.getText() + "\", city = \"" + txfCity.getText() + "\", district = \"" + txfDistrict.getText() + "\""
                    + " WHERE address.address_id = " + employee.getAddressID();

            affactedRows = database.update(query);

            if (affactedRows <= 0) {
                database.rollback();
                database.setAutoCommit(true);
                database.disconnect();
                return -1;
            } else {
                status = 1;
            }

            queryBuilder.clear();
            queryBuilder.set("employee_salary.basic", "" + spBasic.getValue());
            queryBuilder.set("employee_salary.conveyance_allowance", "" + spConveyance.getValue());
            queryBuilder.set("employee_salary.house_rent_allowance", "" + spHouseRent.getValue());
            queryBuilder.set("employee_salary.income_tax", "" + spIncomeTax.getValue());
            queryBuilder.set("employee_salary.medical_allowance", "" + spMedical.getValue());
            queryBuilder.set("employee_salary.provident_fund", "" + spProvidentFund.getValue());
            queryBuilder.where("employee_salary.employee_salary_id = " + employee.getSalary().getSalaryID());

            affactedRows = database.update(queryBuilder.update("employee_salary"));

            if (affactedRows <= 0) {
                database.rollback();
                database.setAutoCommit(true);
                database.disconnect();
                return -1;
            } else {
                status = 1;
            }

            database.setAutoCommit(true);
            database.disconnect();

        } else {
            status = -2;
        }

        return status;
    }

    private int save() {

        MySQLDatabase database = new MySQLDatabase();
        String query;
        QueryBuilder queryBuilder = new QueryBuilder();

        if (database.connect()) {

            database.setAutoCommit(false);

            query = "INSERT INTO address VALUES("
                    + "null, \"" + taAddress.getText() + "\", \"" + txfCity.getText() + "\", \"" + txfDistrict.getText() + "\""
                    + ")";

            long addressID = database.insert(query);

            if (addressID == -1) {
                database.rollback();
                database.setAutoCommit(true);
                database.disconnect();
                return -1;
            }

            query = "INSERT INTO person VALUES("
                    + "null, \"" + txfFirstName.getText() + "\", \"" + txfLastName.getText() + "\", \"" + cmbGender.getSelectedItem().toString() + "\", "
                    + "\"" + Utilities.dateForDB(dcDOB.getSelectedDate().getTime()) + "\", \"" + txfPhone.getText() + "\", " + addressID + ""
                    + ")";

            long personID = database.insert(query);

            if (personID == -1) {
                database.rollback();
                database.setAutoCommit(true);
                database.disconnect();
                return -1;
            }

            long deptID = -1;

            if (cmbDepartment.getSelectedIndex() == -1) {
                queryBuilder.clear();
                queryBuilder.setString("title", cmbDepartment.getSelectedItem().toString());
                deptID = database.insert(queryBuilder.insert("employee_department"));
            } else {
                deptID = departments.get(cmbDepartment.getSelectedIndex());
            }

            if (deptID == -1) {
                database.rollback();
                database.setAutoCommit(true);
                database.disconnect();
                return -1;
            }

            long designationID = -1;

            if (cmbDesgination.getSelectedIndex() == -1) {
                queryBuilder.clear();
                queryBuilder.setString("title", cmbDesgination.getSelectedItem().toString());
                designationID = database.insert(queryBuilder.insert("employee_designation"));
            } else {
                designationID = designations.get(cmbDesgination.getSelectedIndex());
            }

            long salaryID = -1;

            queryBuilder.clear();
            queryBuilder.set("basic", "" + spBasic.getValue());
            queryBuilder.set("house_rent_allowance", "" + spHouseRent.getValue());
            queryBuilder.set("medical_allowance", "" + spMedical.getValue());
            queryBuilder.set("conveyance_allowance", "" + spConveyance.getValue());
            queryBuilder.set("income_tax", "" + spIncomeTax.getValue());
            queryBuilder.set("provident_fund", "" + spProvidentFund.getValue());
            queryBuilder.set("last_updated_by", "" + parentFrame.currentUser.getUserID());
            queryBuilder.set("last_updated_time", "NOW()");

            salaryID = database.insert(queryBuilder.insert("employee_salary"));

            if (salaryID == -1) {
                database.rollback();
                database.setAutoCommit(true);
                database.disconnect();
                return -1;
            }

            queryBuilder.clear();
            queryBuilder.set("person_id", "" + personID);
            queryBuilder.set("department_id", "" + deptID);
            queryBuilder.set("designation_id", "" + designationID);
            queryBuilder.setString("joining_date", "" + Utilities.dateForDB(dcJoiningDate.getSelectedDate().getTime()));
            queryBuilder.set("status", "1");
            queryBuilder.set("salary_id", "" + salaryID);
            queryBuilder.set("last_updated_by", "" + parentFrame.currentUser.getUserID());
            queryBuilder.set("last_updated_time", "NOW()");

            long employeeID = database.insert(queryBuilder.insert("employee"));

            if (employeeID != -1) {
                database.commit();
            } else {
                database.rollback();
            }

            database.setAutoCommit(true);
            database.disconnect();

            return (employeeID == -1) ? -1 : 1;

        } else {
            Message.dbConnectFailed();
            return -1;
        }

    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnReset;
    private javax.swing.JButton btnSave;
    private javax.swing.JComboBox cmbDepartment;
    private javax.swing.JComboBox cmbDesgination;
    private javax.swing.JComboBox cmbGender;
    private datechooser.beans.DateChooserCombo dcDOB;
    private datechooser.beans.DateChooserCombo dcJoiningDate;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JSpinner spBasic;
    private javax.swing.JSpinner spConveyance;
    private javax.swing.JSpinner spHouseRent;
    private javax.swing.JSpinner spIncomeTax;
    private javax.swing.JSpinner spMedical;
    private javax.swing.JSpinner spProvidentFund;
    private javax.swing.JTextArea taAddress;
    private javax.swing.JTextField txfCity;
    private javax.swing.JTextField txfDistrict;
    private javax.swing.JTextField txfFirstName;
    private javax.swing.JTextField txfLastName;
    private javax.swing.JTextField txfPhone;
    // End of variables declaration//GEN-END:variables

    private boolean check() {

        if (!Utilities.isValidString(txfPhone.getText())) {
            parentFrame.showMessage("Employee phone no Required");
            return false;
        }
        if (!Utilities.isValidString(txfFirstName.getText())) {
            parentFrame.showMessage("Employee first name required");
            return false;
        }
        if (!Utilities.isValidString(txfLastName.getText())) {
            parentFrame.showMessage("Employee last name required");
            return false;
        }

        if (cmbGender.getSelectedIndex() == 0) {
            parentFrame.showMessage("Select Employee gender");
            return false;
        }

        if (Double.parseDouble(spBasic.getValue().toString()) < 0.0) {
            parentFrame.showMessage("Invalid amount for Salary Baisc");
            return false;
        }

        return true;
    }

    private void loadDepartment() {

        Vector<String> deptNames = new Vector<>();
        MySQLDatabase database = new MySQLDatabase();
        QueryBuilder query = new QueryBuilder();

        cmbDepartment.removeAllItems();
        departments.clear();

        if (database.connect()) {
            try {
                query.select("*");
                query.from("employee_department");

                ResultSet resultSet = database.get(query.get());
                deptNames.add("Select Department");

                while (resultSet.next()) {

                    departments.put(deptNames.size(), resultSet.getInt("department_id"));
                    deptNames.add(resultSet.getString("title"));
                }

                cmbDepartment.setModel(new DefaultComboBoxModel(deptNames));

            } catch (SQLException ex) {
                Logger.getLogger(UIEmployee.class.getName()).log(Level.SEVERE, null, ex);
            }


        } else {
            parentFrame.showMessage("Unable to load departments");
        }
    }

    private void loadDesignation() {

        Vector<String> designationTitles = new Vector<>();
        MySQLDatabase database = new MySQLDatabase();
        QueryBuilder query = new QueryBuilder();

        cmbDesgination.removeAllItems();
        designations.clear();

        if (database.connect()) {
            try {

                query.select("*");
                query.from("employee_designation");

                ResultSet resultSet = database.get(query.get());
                designationTitles.add("Select Desgination");

                while (resultSet.next()) {

                    designations.put(designationTitles.size(), resultSet.getInt("designation_id"));
                    designationTitles.add(resultSet.getString("title"));
                }

                cmbDesgination.setModel(new DefaultComboBoxModel(designationTitles));

            } catch (SQLException ex) {
                Logger.getLogger(UIEmployee.class.getName()).log(Level.SEVERE, null, ex);
            }


        } else {
            parentFrame.showMessage("Unable to load designation");
        }
    }
}
