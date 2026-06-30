package appswing;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import modelo.Genero;
import requisitos.RequisitoGenero;

public class TelaGenero {
    private JDialog frame;
    private JTable table;
    private JScrollPane scrollPane;
    private JButton btnCriar;
    private JButton btnApagar;
    private JButton btnLimpar;
    private JLabel lblMensagem;
    private JLabel lblResultados;
    private JLabel lblNome;
    private JLabel lblId;
    private JTextField txtNome;
    private JTextField txtId;

    private RequisitoGenero requisito;

    public TelaGenero() {
        requisito = new RequisitoGenero();
        initialize();
    }

    private void initialize() {
        frame = new JDialog();
        frame.setResizable(false);
        frame.setModal(true);
        frame.setTitle("Gerenciar Gêneros");
        frame.setBounds(100, 100, 500, 450); 
        frame.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        frame.getContentPane().setLayout(null);
        
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent arg0) {
                listagem();
            }
        });

        scrollPane = new JScrollPane();
        scrollPane.setBounds(21, 39, 440, 150);
        frame.getContentPane().add(scrollPane);

        table = new JTable() {
            public boolean isCellEditable(int rowIndex, int vColIndex) {
                return false;
            }
        };

        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    lblMensagem.setText("");
                    if (table.getSelectedRow() >= 0) {
                        Integer idGenero = (Integer) table.getValueAt(table.getSelectedRow(), 0);
                        String nomeGenero = (String) table.getValueAt(table.getSelectedRow(), 1);
                        
                        txtId.setText(String.valueOf(idGenero));
                        txtNome.setText(nomeGenero);
                    }
                } catch (Exception erro) {
                    lblMensagem.setText("Erro: " + erro.getMessage());
                }
            }
        });

        table.setGridColor(Color.BLACK);
        table.setBackground(Color.WHITE);
        table.setFillsViewportHeight(true);
        table.setRowSelectionAllowed(true);
        table.setFont(new Font("Tahoma", Font.PLAIN, 12));
        scrollPane.setViewportView(table);
        table.setBorder(new LineBorder(new Color(0, 0, 0)));
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setShowGrid(true);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        lblMensagem = new JLabel("");
        lblMensagem.setForeground(Color.RED);
        lblMensagem.setBounds(21, 370, 440, 14);
        frame.getContentPane().add(lblMensagem);

        lblResultados = new JLabel("Selecione um gênero para gerenciar");
        lblResultados.setBounds(21, 200, 400, 14);
        frame.getContentPane().add(lblResultados);

        lblNome = new JLabel("Nome:");
        lblNome.setHorizontalAlignment(SwingConstants.RIGHT);
        lblNome.setFont(new Font("Tahoma", Font.PLAIN, 11));
        lblNome.setBounds(21, 230, 50, 14);
        frame.getContentPane().add(lblNome);
        
        txtNome = new JTextField();
        txtNome.setFont(new Font("Tahoma", Font.PLAIN, 12));
        txtNome.setColumns(10);
        txtNome.setBounds(80, 226, 250, 20);
        frame.getContentPane().add(txtNome);

        lblId = new JLabel("ID:");
        lblId.setHorizontalAlignment(SwingConstants.RIGHT);
        lblId.setFont(new Font("Tahoma", Font.PLAIN, 11));
        lblId.setBounds(21, 260, 50, 14);
        frame.getContentPane().add(lblId);

        txtId = new JTextField();
        txtId.setEditable(false);
        txtId.setFont(new Font("Tahoma", Font.PLAIN, 12));
        txtId.setColumns(10);
        txtId.setBounds(80, 256, 50, 20);
        frame.getContentPane().add(txtId);

        btnCriar = new JButton("Criar");
        btnCriar.setBounds(21, 310, 80, 25);
        btnCriar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                criarGenero();
            }
        });
        frame.getContentPane().add(btnCriar);

        btnApagar = new JButton("Apagar");
        btnApagar.setBounds(111, 310, 80, 25);
        btnApagar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                apagarGenero();
            }
        });
        frame.getContentPane().add(btnApagar);

        btnLimpar = new JButton("Limpar");
        btnLimpar.setBounds(201, 310, 80, 25);
        btnLimpar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                txtNome.setText("");
                txtId.setText("");
                lblMensagem.setText("");
            }
        });
        frame.getContentPane().add(btnLimpar);

        frame.setVisible(true);
    }

    private void listagem() {
        try {
            DefaultTableModel model = new DefaultTableModel();
            table.setModel(model);

            model.addColumn("ID");
            model.addColumn("Nome do Gênero");

            List<Genero> lista = requisito.listarTodos();

            for (Genero g : lista) {
                model.addRow(new Object[] { g.getId(), g.getNome() });
            }

            lblResultados.setText("Resultados: " + lista.size() + " gêneros cadastrados");
        } catch (Exception erro) {
            lblMensagem.setText("Erro ao listar: " + erro.getMessage());
        }
    }

    private void criarGenero() {
        try {
            if (txtNome.getText().isEmpty()) {
                lblMensagem.setText("O nome do gênero é obrigatório.");
                return;
            }
            
            String nome = txtNome.getText().trim();
            requisito.cadastrarGenero(nome);
            
            lblMensagem.setText("Gênero cadastrado com sucesso!");
            listagem();
            btnLimpar.doClick(); // Limpa os campos após salvar
            
        } catch (Exception ex) {
            lblMensagem.setText("Erro: " + ex.getMessage());
        }
    }

    private void apagarGenero() {
        try {
            if (txtId.getText().isEmpty()) {
                lblMensagem.setText("Selecione um gênero na tabela para apagar.");
                return;
            }
            
            int id = Integer.parseInt(txtId.getText());
            String nome = txtNome.getText();
            
            Object[] options = { "Confirmar Exclusão", "Cancelar" };
            int escolha = JOptionPane.showOptionDialog(null, 
                "Deseja realmente apagar o gênero '" + nome + "'?", 
                "Alerta", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, 
                null, options, options[1]);
            
            if (escolha == 0) {
                requisito.excluirGenero(id);
                lblMensagem.setText("Gênero excluído com sucesso!");
                listagem();
                btnLimpar.doClick();
            }
        } catch (Exception erro) {
            lblMensagem.setText("Erro ao apagar: " + erro.getMessage());
        }
    }
}
