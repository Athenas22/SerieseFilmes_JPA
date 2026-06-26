package appswing;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import util.Util;

public class TelaPrincipal {
    private JFrame frame;
    private JMenu mnSerie;
    private JMenu mnGenero;
    private JMenu mnEpisodio;
    private JMenu mnConsulta;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    TelaPrincipal window = new TelaPrincipal();
                    window.frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public TelaPrincipal() {
        initialize();
    }

    private void initialize() {
        frame = new JFrame();
        frame.setTitle("Controle de Séries e Filmes - JPA");
        frame.setBounds(100, 100, 500, 350);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null); // Layout absoluto igual ao do professor
        frame.setResizable(false);
        
        // --- GERENCIAMENTO DE CONEXÃO JPA ---
        // Conecta ao abrir e desconecta ao fechar no "X"
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent e) {
                try {
                    Util.conectar();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(frame, "Erro ao conectar com o banco: " + ex.getMessage());
                }
            }
            @Override
            public void windowClosing(WindowEvent e) {
                try {
                    Util.desconectar();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
        
        // --- TEXTO CENTRAL DA TELA ---
        JLabel label = new JLabel("Sistema de Gerenciamento");
        label.setFont(new Font("Tahoma", Font.BOLD, 24));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setForeground(new java.awt.Color(0, 102, 204));
        label.setBounds(0, 0, 484, 289);
        frame.getContentPane().add(label);

        // --- BARRA DE MENUS ---
        JMenuBar menuBar = new JMenuBar();
        frame.setJMenuBar(menuBar);
        
        mnSerie = new JMenu("Série");
        mnSerie.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                new TelaSerie(); 
                JOptionPane.showMessageDialog(frame, "Criaremos a TelaSerie a seguir!");
            }
        });
        menuBar.add(mnSerie);
        
        mnGenero = new JMenu("Gênero");
        mnGenero.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                new TelaGenero(); 
                JOptionPane.showMessageDialog(frame, "Criaremos a TelaGenero a seguir!");
            }
        });
        menuBar.add(mnGenero);

        mnEpisodio = new JMenu("Episódio");
        mnEpisodio.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                new TelaEpisodio(); 
                JOptionPane.showMessageDialog(frame, "Criaremos a TelaEpisodio a seguir!");
            }
        });
        menuBar.add(mnEpisodio);
        
        mnConsulta = new JMenu("Consulta");
        mnConsulta.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                new TelaConsulta(); 
                JOptionPane.showMessageDialog(frame, "Criaremos a TelaConsulta a seguir!");
            }
        });
        menuBar.add(mnConsulta);
    }
}