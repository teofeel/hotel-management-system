package views;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import controller.*;
import manager.*;

public class LoginView extends JFrame{
    // Components of the Form
    private Container container;
    private JLabel userLabel;
    private JTextField userTextField;
    private JLabel passwordLabel;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton resetButton;
    private JLabel messageLabel;

    public LoginView() {
    	FileManager.getInstance().ocistiPodatkeAplikacija();
    	FileManager.getInstance().ucitajPodatke();
    	DodatnoManager.getInstance().odbijIstekleRezervacije();
    	
        setTitle("Login Form");
        setBounds(300, 90, 400, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);

        container = getContentPane();
        container.setLayout(null);

        userLabel = new JLabel("Korisnicko ime:");
        userLabel.setFont(new Font("Arial", Font.PLAIN, 15));
        userLabel.setSize(100, 20);
        userLabel.setLocation(50, 50);
        container.add(userLabel);

        userTextField = new JTextField();
        userTextField.setFont(new Font("Arial", Font.PLAIN, 15));
        userTextField.setSize(200, 20);
        userTextField.setLocation(150, 50);
        container.add(userTextField);

        passwordLabel = new JLabel("Lozinka:");
        passwordLabel.setFont(new Font("Arial", Font.PLAIN, 15));
        passwordLabel.setSize(100, 20);
        passwordLabel.setLocation(50, 100);
        container.add(passwordLabel);

        passwordField = new JPasswordField();
        passwordField.setFont(new Font("Arial", Font.PLAIN, 15));
        passwordField.setSize(200, 20);
        passwordField.setLocation(150, 100);
        container.add(passwordField);

        loginButton = new JButton("Login");
        loginButton.setFont(new Font("Arial", Font.PLAIN, 15));
        loginButton.setSize(100, 20);
        loginButton.setLocation(50, 150);
        loginButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
                String username = userTextField.getText();
                String password = new String(passwordField.getPassword());
                
                String login = LoginController.checkLogin(username,password);
                
                if (login.equals("admin")) {
                    AdminViews admin = new AdminViews(username);
                    admin.setVisible(true);
                    dispose();
                }
                else if(login.equals("recepcioner")){
                	RecepcionerViews recepcioner = new RecepcionerViews(username);
                	recepcioner.setVisible(true);
                	dispose();
                }
                else if(login.equals("sobarica")){
                	SobaricaViews sobarica = new SobaricaViews(username);
                	sobarica.setVisible(true);
                	dispose();
                }
                else if(login.equals("gost")){
                	GostViews gost = new GostViews(username);
                	gost.setVisible(true);
                	dispose();
                }
                else {
                	getMessageLabel().setText("Korisnicko ime i lozinka se ne podudrajau");
                }
            }
		});
        container.add(loginButton);

        resetButton = new JButton("Reset");
        resetButton.setFont(new Font("Arial", Font.PLAIN, 15));
        resetButton.setSize(100, 20);
        resetButton.setLocation(250, 150);
        resetButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				userTextField.setText("");
				passwordField.setText("");
				messageLabel.setText("");
			}
		});
        
        container.add(resetButton);

        messageLabel = new JLabel("");
        messageLabel.setFont(new Font("Arial", Font.PLAIN, 15));
        messageLabel.setSize(300, 20);
        messageLabel.setLocation(50, 200);
        container.add(messageLabel);

        setVisible(true);
    }
    
    public JTextField getUserTextField() {
        return userTextField;
    }

    public JPasswordField getPasswordField() {
        return passwordField;
    }

    public JButton getLoginButton() {
        return loginButton;
    }

    public JButton getResetButton() {
        return resetButton;
    }

    public JLabel getMessageLabel() {
        return messageLabel;
    }  
}
