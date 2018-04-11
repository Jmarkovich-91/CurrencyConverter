/*
 * Group 5 Project
 * http://fixer.io/
 * https://www.ecb.europa.eu/stats/policy_and_exchange_rates/euro_reference_exchange_rates/html/index.en.html
 */

import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;

/**
 *
 * @author michael
 */
public class Converter extends JPanel {
    
   private JFrame mainFrame;
   private JLabel titleImage, convertRate;   
   private JPanel controlPanel;
   private JTextField inputCurrency;
   private JLabel outputCurrency;
   private JComboBox inputCntry, outputCntry;
   private final String nzVal = "0.00";
   private ImageIcon inputFlag, outputFlag;
   private JLabel inImage, outImage;
   private JLabel inFlag, outFlag;
   private JLayeredPane layeredPane;
   private DefaultComboBoxModel countryList, countryList2;
   private JButton quitButton, clearButton, convertButton, userGuide;
   private final String welcome = "The new Currency Converter";
   private BufferedImage newFlag;
   private double testNumIn = 0.0;
   private List<String> nameList = new ArrayList<>();
   private String nameChart[][];
   
   public Converter(){
       //setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

        layeredPane = new JLayeredPane();
        layeredPane.setPreferredSize(new Dimension(240, 210));
        blueBorder();    

        layeredPane.setLayout(new GridLayout(3,2));
        setLabels();
        //setFlags(countryList.getSelectedItem().toString(), countryList2.getSelectedItem().toString());
        setFlags();
        layeredPane.add(inputCntry);
        layeredPane.add(outputCntry);
        layeredPane.add(inputCurrency);
        layeredPane.add(outputCurrency);
        layeredPane.add(inFlag);
        layeredPane.add(outFlag);
        nameList();
        showMain();
        

   }
    public static void main(String[] args) {
                
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                createAndShowGUI();
            }
        });
    }

    private static void createAndShowGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame("Currency Converter™");
        frame.setAlwaysOnTop(true);
        frame.setResizable(false);
        frame.setSize(800,800);
        
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        //Create and set up the content pane.
        JComponent newContentPane = new Converter();
        newContentPane.setBackground(Color.DARK_GRAY);
        newContentPane.setOpaque(true); //content panes must be opaque
        frame.setContentPane(newContentPane);
        newContentPane.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        
        //Display the window.
        //frame.pack();
        frame.setVisible(true);
    }
    private void setLabels(){
        
      inputCurrency = new JTextField(10);
      inputCurrency.setText(nzVal);
      inputCurrency.setFont(new Font("Serif", Font.BOLD, 18));
      inputCurrency.setHorizontalAlignment(SwingConstants.CENTER);
      inputCurrency.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
      inputCurrency.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e){
                inputCurrency.setText("");
                blueBorder();
            }
        });
        
      outputCurrency = new JLabel();
      outputCurrency.setOpaque(true);
      outputCurrency.setText(nzVal);
      outputCurrency.setFont(new Font("Serif", Font.BOLD, 18));
      outputCurrency.setHorizontalAlignment(SwingConstants.CENTER);      
      outputCurrency.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));      

      createModels();
      inputCntry = new JComboBox(countryList);
      inputCntry.setFont(new Font("Serif", Font.BOLD, 20));
      inputCntry.setSelectedIndex(0);
      inputCntry.setForeground(Color.blue);
      inputCntry.addActionListener(actionListener);
      
      outputCntry = new JComboBox(countryList2);
      outputCntry.setFont(new Font("Serif", Font.BOLD, 20));
      outputCntry.setSelectedIndex(countryList2.getSize()-1);
      outputCntry.setForeground(Color.blue);
      outputCntry.addActionListener(actionListener2);      
    }    
    private void createModels(){
      FlagImages flag = new FlagImages();
      Vector vect = new Vector();
      vect.addAll(flag.getList());
      countryList = new DefaultComboBoxModel(vect);      
      countryList2 = new DefaultComboBoxModel(vect);
    }
    ActionListener actionListener = new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent actionEvent) {
        String name = (String)inputCntry.getSelectedItem();
        changeFlags(name);
        inFlag.setIcon(new ImageIcon(newFlag));        
      }
    };
    ActionListener actionListener2 = new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent actionEvent) {
        String name = (String)outputCntry.getSelectedItem();
        changeFlags(name);
        outFlag.setIcon(new ImageIcon(newFlag));
      }
    };
    private void changeFlags(String name){                        
          try {
              newFlag = ImageIO.read(getClass().getResource(selectFlag(name)));              
          } catch (IOException ex) {
              Logger.getLogger(Converter.class.getName()).log(Level.SEVERE, null, ex);
          }           
    }
    private String selectFlag(String name){
        //queued on img.Flags package folder
        return "/Flags/" + name + ".png";
    }
    //set flags initially & after changes...layout here?
    private void setFlags(){            
        try {
        BufferedImage imgIn = ImageIO.read(getClass().getResource(selectFlag((String)inputCntry.getSelectedItem())));
        inFlag = new JLabel(new ImageIcon(imgIn));
        inFlag.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
        } catch (IOException ex) {
            System.out.println("Can't do input");
        }
        try {
        BufferedImage imgOut = ImageIO.read(getClass().getResource(selectFlag((String)outputCntry.getSelectedItem())));
        outFlag = new JLabel(new ImageIcon(imgOut));
        outFlag.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
        } catch (IOException ex) {
            System.out.println("Can't do input");
        }
    }

    private void showMain(){
        controlPanel = new JPanel((new GridBagLayout()));
        GridBagConstraints c = new GridBagConstraints();        
        final ImageIcon icon = createImageIcon("/Flags/NewBackground.png");
        //Create and set up the layered pane

        titleImage = new JLabel(icon);
        if (icon == null) {
            System.err.println("icon not found; using black rectangle instead.");
            titleImage.setOpaque(true);
            titleImage.setBackground(Color.BLACK);
            }
        
            convertRate = new JLabel();
            Font font = convertRate.getFont();  
            convertRate.setFont(font.deriveFont(Font.BOLD));
            convertRate.setText(" Conversion Rate: ");
            c.fill = GridBagConstraints.HORIZONTAL;
            c.ipady = 15;      //make this component tall
            c.weightx = 1;
            c.gridx = 0;
            c.gridy = 7;
            controlPanel.add(convertRate, c);

            
            userGuide = new JButton("UserGuide");
            userGuide.setBackground(Color.LIGHT_GRAY);
            userGuide.setContentAreaFilled(true);
            userGuide.setForeground(Color.black);
            userGuide.addActionListener((ActionEvent ex) -> {
                JOptionPane.showMessageDialog(null, getUserGuide());
                });
            c.fill = GridBagConstraints.NONE;
            c.weightx = 1;
            c.gridx = 0;
            c.gridy = 9;
            controlPanel.add(userGuide, c);
            
            clearButton = new JButton("Clear");
            clearButton.setBackground(Color.blue);
            clearButton.setContentAreaFilled(true);
            clearButton.setForeground(Color.black);
            clearButton.addActionListener((ActionEvent ex) -> {
                inputCurrency.setText(nzVal);
                outputCurrency.setText(nzVal);
                convertRate.setText(" Conversion Rate: ");
                blueBorder();
                });
            c.fill = GridBagConstraints.NONE;
            c.weightx = 1;
            c.gridx = 2;
            c.gridy = 7;
            controlPanel.add(clearButton, c);

            convertButton = new JButton("Convert");
            convertButton.setBackground(Color.green);
            convertButton.setContentAreaFilled(true);
            convertButton.addActionListener((ActionEvent e) -> {
                try{
                if (inputCntry.getSelectedItem() == outputCntry.getSelectedItem()) {
                    throw new Exception("Cannot convert between same currencies.");
                }                
                if (!valueCheck(inputCurrency.getText())){  //not procing redBorder
                    testNumIn = 0.0;
                    throw new Exception("Currency inputs must be numerical.");
                }
                DecimalFormat df = new DecimalFormat("#,###,###,##0.00" ); 
                if (Math.signum(testNumIn) < 0) {
                    throw new Exception ("Input cannot be negative.");
                }
                inputCurrency.setText(String.valueOf(df.format(testNumIn)));                
                //get conversion; do the math
                double conversionRate = getConvRate(inputCntry.getSelectedItem().toString(), outputCntry.getSelectedItem().toString());
                
                    System.out.println(testNumIn + " @rate of " + conversionRate + " = " + testNumIn * conversionRate);
                    
                testNumIn = testNumIn * conversionRate;
                
                outputCurrency.setText(String.valueOf(df.format(testNumIn)));
                convertRate.setText("  Conversion Rate: " + conversionRate);
                //set to green border                
                greenBorder();
                
                  }catch (Exception ex) {
                      redBorder();
                        
                        JOptionPane.showMessageDialog(layeredPane, ex.getMessage());
                        inputCurrency.setText(nzVal);
                        outputCurrency.setText(nzVal);                        
                  }
        });
                
            c.fill = GridBagConstraints.CENTER;
            //c.weightx = 0.5;
            c.gridx = 1;
            c.gridy = 7;
            controlPanel.add(convertButton, c);
                        
            //Add control pane and layered pane to this JPanel.
            add(Box.createRigidArea(new Dimension(0, 10)));
            add(Box.createRigidArea(new Dimension(0, 10)));

            c.fill = GridBagConstraints.HORIZONTAL;
            c.ipady = 15;      //make this component tall
            c.weighty = 0;   //request any extra vertical space
            c.weightx = 1.0;   //request any extra horizontal space
            c.anchor = GridBagConstraints.PAGE_START; //top of space
            c.insets = new Insets(10,0,0,0);  //top padding
            c.gridx = 0;       //aligned with left side
            c.gridwidth = 3;   //3 columns wide
            c.gridheight = 1;
            c.gridy = 0;
            controlPanel.add(titleImage, c);

            //Add control pane and layered pane to this JPanel.
            add(Box.createRigidArea(new Dimension(0, 10)));
            add(Box.createRigidArea(new Dimension(0, 10)));

            c.anchor = GridBagConstraints.CENTER;            
            c.weightx = 1.0;
            c.gridx = 0;
            c.gridy = 5;
            c.insets = new Insets(10,10,10,10);  //full padding
            controlPanel.add(layeredPane, c);

            quitButton = new JButton("Quit");
            quitButton.setBackground(Color.red);
            quitButton.setContentAreaFilled(true);
            quitButton.setForeground(Color.black);
            quitButton.addActionListener((ActionEvent ex) -> {
                System.exit(0);
                });
            c.fill = GridBagConstraints.CENTER;
            c.weightx = 1;
            c.anchor = GridBagConstraints.PAGE_END; //bottom of space
            c.gridx = 0;
            c.gridy = 9;
            controlPanel.add(quitButton, c);           

            
            this.add(controlPanel);
    }
    
   /** Returns an ImageIcon, or null if the path was invalid.
     * @param path
     * @return  */
    protected static ImageIcon createImageIcon(String path) {
        java.net.URL imgURL = Converter.class.getResource(path);
        if (imgURL != null) {
            return new ImageIcon(imgURL);
        } else {
            System.err.println("Couldn't find this file: " + path);
            return null;
        }
    }
    private Boolean valueCheck(String value){
        try{
        testNumIn = Double.parseDouble(value.trim());              
        }catch (Exception Num){
            inputCurrency.setText(nzVal);
            outputCurrency.setText(nzVal);
            redBorder();
            return false;
        }        
        return true;
    }
    private String getUserGuide()
    {
        String info = "This is where userguide info goes";        
        return info;
    }
    private void borderHelper(Color color, String title) {
        layeredPane.setBorder(BorderFactory.createTitledBorder(BorderFactory.createMatteBorder(10, 10,
                10, 10, color), title, TitledBorder.CENTER, TitledBorder.TOP));

    }

    private void greenBorder() {
        borderHelper(Color.green, "Converted Successfully!");
    }

    private void redBorder() {
        borderHelper(Color.red, "Unable to convert successfully. Try again!");
    }

    private void blueBorder() {
        borderHelper(Color.blue, welcome);
    }    
    private double getConvRate(String in, String out)
    {
        String input = in;
        String output = out;
        
        for (int i = 0; i < nameChart[0].length; i++)
        {
            if (nameChart[0][i] == null ? in == null : nameChart[0][i].equals(in))
            {
                in = nameChart[1][i];
                System.out.println("In " + in);
            }
        }
        for (int i = 0; i < nameChart[0].length; i++)
        {
            if (nameChart[0][i] == null ? out == null : nameChart[0][i].equals(out))
            {
                out = nameChart[1][i];
                System.out.println("out " + out);
            }
        }        
        ConverterAPI api = new ConverterAPI();
        return api.convert(in, out);
    }
    private void nameList()
    {
        FlagImages flag = new FlagImages();
        nameChart = new String[2][flag.list.size()];
        for (int i = 0; i < 2; i++)
        {
            flag.getFileData(i);
            
            for (int j = 0; j < flag.list.size(); j ++)
            {
                nameChart[i][j] = flag.list.get(j);
            }
        }        
    } 
}
