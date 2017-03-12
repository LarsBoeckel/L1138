
package pascalcompiler;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;
public class UI extends javax.swing.JFrame {
    private String code;
    private String docfolder;
    private String homefolder;
    private String codedir;
    private String progName;
    private String runTime;
    
    public UI() {
        initComponents();
        myInitcomponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jButton1 = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextArea2 = new javax.swing.JTextArea();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Pascal Compiler");

        jLabel1.setText("Insert your Code and click Compile:");

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        jButton1.setText("Compile");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jTextArea2.setColumns(20);
        jTextArea2.setRows(5);
        jScrollPane2.setViewportView(jTextArea2);

        jLabel2.setText("Translated Code:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 315, Short.MAX_VALUE)
                    .addComponent(jScrollPane2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        try {
            compileButton();
        } catch (IOException ex) {
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    public static void main(String args[]) {
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
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(UI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        
        //</editor-fold>

        java.awt.EventQueue.invokeLater(() -> {
            new UI().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextArea jTextArea2;
    // End of variables declaration//GEN-END:variables

    private void myInitcomponents() 
    {
        setTitle("Pascal Compiler");
        setResizable(false);
    }
    private void compileButton() throws IOException
    {
        jButton1.setText("Working...");
        code = jTextArea1.getText();
        translate();
        stringToFile();
        compile();
        jButton1.setText("Compile");
    }

    private void translate()
    {
        code = code.replace("{*", "/*");
        code = code.replace("*}", "*/");
        code = code.replace("{", "/*");
        code = code.replace("}", "*/");
        code = code.replace ("end;", "}");
        code = code.replaceFirst(";", " ");
        code = code.replaceFirst("end.", "}  } ");
        String[] progNameTemp = code.split(" ");
        progName = progNameTemp[1];
        code = code.replaceFirst(progName, progName + "{");
        if(code.contains("var"))
        {
            code = code.replace("shortint", "public int");
            code = code.replace("byte", "public int");
            code = code.replace("integer", "public int");
            code = code.replace("word", "public String");
            code = code.replace("longint", "public long");
            code = code.replace("real", "public float");
            code = code.replace("String", "public String");
            code = code.replace("string", "public String");
            code = code.replace(":=", "=");
            code = code.replaceFirst("var", "begin");
            String[] onlyVariablesArray = code.split("begin");
            String variables= onlyVariablesArray[1];
            variables = variables.replace(":", ";");
            String[] variablesArray = variables.split(";");
            int vAlength = variablesArray.length;
            if(vAlength == 18)
            {
            code = code.replaceFirst("begin;", variablesArray[2]+" "+variablesArray[1]+";"+variablesArray[4]+" "+variablesArray[3]+";"+variablesArray[6]+" "+variablesArray[5]+";"+variablesArray[8]+" "+variablesArray[7]+";"+variablesArray[10]+" "+variablesArray[9]+";"+variablesArray[12]+" "+variablesArray[11]+";"+variablesArray[14]+" "+variablesArray[13]+";"+variablesArray[16]+" "+variablesArray[15]+";"+variablesArray[18]+" "+variablesArray[17]+";");
            }
            if(vAlength == 16)
            {
            code = code.replaceFirst("begin;", variablesArray[2]+" "+variablesArray[1]+";"+variablesArray[4]+" "+variablesArray[3]+";"+variablesArray[6]+" "+variablesArray[5]+";"+variablesArray[8]+" "+variablesArray[7]+";"+variablesArray[10]+" "+variablesArray[9]+";"+variablesArray[12]+" "+variablesArray[11]+";"+variablesArray[14]+" "+variablesArray[13]+";"+variablesArray[16]+" "+variablesArray[15]);
            }
            if(vAlength == 14)
            {
            code = code.replaceFirst("begin;", variablesArray[2]+" "+variablesArray[1]+";"+variablesArray[4]+" "+variablesArray[3]+";"+variablesArray[6]+" "+variablesArray[5]+";"+variablesArray[8]+" "+variablesArray[7]+";"+variablesArray[10]+" "+variablesArray[9]+";"+variablesArray[12]+" "+variablesArray[11]+";"+variablesArray[14]+" "+variablesArray[13]);
            }
            if(vAlength == 12)
            {
            code = code.replaceFirst("begin;", variablesArray[2]+" "+variablesArray[1]+";"+variablesArray[4]+" "+variablesArray[3]+";"+variablesArray[6]+" "+variablesArray[5]+";"+variablesArray[8]+" "+variablesArray[7]+";"+variablesArray[10]+" "+variablesArray[9]+";"+variablesArray[12]+" "+variablesArray[11]);
            }
            if(vAlength == 10)
            {
            code = code.replaceFirst("begin;", variablesArray[2]+" "+variablesArray[1]+";"+variablesArray[4]+" "+variablesArray[3]+";"+variablesArray[6]+" "+variablesArray[5]+";"+variablesArray[8]+" "+variablesArray[7]+";"+variablesArray[10]+" "+variablesArray[9]);
            }
            if(vAlength == 8)
            {
            code = code.replaceFirst("begin;", variablesArray[2]+" "+variablesArray[1]+";"+variablesArray[4]+" "+variablesArray[3]+";"+variablesArray[6]+" "+variablesArray[5]+";"+variablesArray[8]+" "+variablesArray[7]);
            }
            if(vAlength == 6)
            {
            code = code.replaceFirst("begin;", variablesArray[2]+" "+variablesArray[1]+";"+variablesArray[4]+" "+variablesArray[3]+";"+variablesArray[6]+" "+variablesArray[5]);
            }
            if(vAlength == 4)
            {
            code = code.replaceFirst("begin;", variablesArray[2]+" "+variablesArray[1]+";"+variablesArray[4]+" "+variablesArray[3]);
            }
            if(vAlength == 2)
            {
            code = code.replaceFirst("begin;", variablesArray[2]+" "+variablesArray[1]+";");
            }
        }
        code = code.replaceFirst("program", "public class");
        code = code.replaceFirst("begin", "public static void main(){");
        code = code.replace("begin", "{");
        code = code.replace("end", "}");
        code = code.replace("writeln", "System.out.println");
        code = code.replace("write", "Systen.out.println");
        code = code.replace("readln", "System.out.println");
        code = code.replace("'", "\"");
        code = code.replace("'", "\"");
        code = code.replace("\")", "\");");
        code = code.replace("if", "if (");
        code = code.replace("then", ")");
        code = code.replace("while","while (");
        code = code.replace(" do", ")");
        
    }

    private void stringToFile() throws IOException 
    {
        homefolder = System.getProperty("user.home");
        codedir = homefolder +"\\" + progName + ".java";
            BufferedWriter exp = new BufferedWriter(new FileWriter(codedir));
            exp.write(code);
            exp.close();
    }

    private void compile() throws IOException 
    {
       File[] files1 = new File[]{new File(codedir)};
       JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
       StandardJavaFileManager fileManager = compiler.getStandardFileManager(null, null, null);
        Iterable<? extends JavaFileObject> compilationUnits1 =
           fileManager.getJavaFileObjectsFromFiles(Arrays.asList(files1));
       compiler.getTask(null, fileManager, null, null, null, compilationUnits1).call();
       fileManager.close();
       
       
        StringBuilder rt= new StringBuilder();
        rt.append(" jar cvf ").append(homefolder).append("\\").append(progName).append(".jar ").append(homefolder).append("\\").append(progName).append(".class");
        runTime = rt.toString();
        Runtime.getRuntime().exec(runTime);
        
        jTextArea2.setText(code);
        jTextArea2.append("\n\n\nYour translated code can also be found in a file called " + progName + ".java,\nprepared for running with "+ progName +".class and a " + progName + ".jar at\n" + homefolder+ ".");
        
    }
}