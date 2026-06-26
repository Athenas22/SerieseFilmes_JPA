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

import modelo.Episodio;
import requisitos.RequisitoEpisodio;
import requisitos.RequisitoSerie;

public class TelaEpisodio {
    private JDialog frame;
    private JTable table;
    private JScrollPane scrollPane;
    private JButton btnCriar;
    private JButton btnApagar;
    private JButton btnLimpar;
    private JLabel lblMensagem;
    private JLabel lblResultados;
    private JLabel lblId;
    private JLabel lblNome;
    private JLabel lblSerie;
    private JTextField txtId;
    private JTextField txtNome;
    private JTextField txtSerie;

    private RequisitoEpisodio requisitoEpisodio;
    private RequisitoSerie requisitoSerie;

    public TelaEpisodio() {
        requisitoEpisodio = new RequisitoEpisodio();
        requisitoSerie = new RequisitoSerie();
        initialize();
    }

    private void initialize() {
        frame = new JDialog();
        frame.setResizable(false);
        frame.setModal(true);
        frame.setTitle("Gerenciar Episódios");
        frame.setBounds(100, 100, 500, 480);
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
                        Integer idEpisodio = (Integer) table.getValueAt(table.getSelectedRow(), 0);
                        String nomeEpisodio = (String) table.getValueAt(table.getSelectedRow(), 1);
                        String nomeSerie = (String) table.getValueAt(table.getSelectedRow(), 2);
                        
                        txtId.setText(String.valueOf(idEpisodio));
                        txtNome.setText(nomeEpisodio);
                        
                        if (nomeSerie != null && !nomeSerie.equals("Sem Série")) {
                            txtSerie.setText(nomeSerie);
                        } else {
                            txtSerie.setText("");
                        }
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
        lblMensagem.setBounds(21, 390, 440, 14);
        frame.getContentPane().add(lblMensagem);

        lblResultados = new JLabel("Selecione um episódio para gerenciar");
        lblResultados.setBounds(21, 200, 400, 14);
        frame.getContentPane().add(lblResultados);

        lblId = new JLabel("ID:");
        lblId.setHorizontalAlignment(SwingConstants.RIGHT);
        lblId.setFont(new Font("Tahoma", Font.PLAIN, 11));
        lblId.setBounds(21, 230, 50, 14);
        frame.getContentPane().add(lblId);

        txtId = new JTextField();
        txtId.setEditable(false);
        txtId.setFont(new Font("Tahoma", Font.PLAIN, 12));
        txtId.setColumns(10);
        txtId.setBounds(80, 226, 50, 20);
        frame.getContentPane().add(txtId);

        lblNome = new JLabel("Nome:");
        lblNome.setHorizontalAlignment(SwingConstants.RIGHT);
        lblNome.setFont(new Font("Tahoma", Font.PLAIN, 11));
        lblNome.setBounds(21, 260, 50, 14);
        frame.getContentPane().add(lblNome);
        
        txtNome = new JTextField();
        txtNome.setFont(new Font("Tahoma", Font.PLAIN, 12));
        txtNome.setColumns(10);
        txtNome.setBounds(80, 256, 250, 20);
        frame.getContentPane().add(txtNome);

        lblSerie = new JLabel("Série:");
        lblSerie.setHorizontalAlignment(SwingConstants.RIGHT);
        lblSerie.setFont(new Font("Tahoma", Font.PLAIN, 11));
        lblSerie.setBounds(21, 290, 50, 14);
        frame.getContentPane().add(lblSerie);
        
        txtSerie = new JTextField();
        txtSerie.setToolTipText("Digite o nome da série à qual este episódio pertence");
        txtSerie.setFont(new Font("Tahoma", Font.PLAIN, 12));
        txtSerie.setColumns(10);
        txtSerie.setBounds(80, 286, 250, 20);
        frame.getContentPane().add(txtSerie);

        btnCriar = new JButton("Criar");
        btnCriar.setBounds(21, 340, 80, 25);
        btnCriar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                criarEpisodio();
            }
        });
        frame.getContentPane().add(btnCriar);

        btnApagar = new JButton("Apagar");
        btnApagar.setBounds(111, 340, 80, 25);
        btnApagar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                apagarEpisodio();
            }
        });
        frame.getContentPane().add(btnApagar);

        btnLimpar = new JButton("Limpar");
        btnLimpar.setBounds(201, 340, 80, 25);
        btnLimpar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                txtId.setText("");
                txtNome.setText("");
                txtSerie.setText("");
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
            model.addColumn("Nome do Episódio");
            model.addColumn("Série");

            List<Episodio> lista = requisitoEpisodio.listarTodos();

            for (Episodio ep : lista) {
                String nomeDaSerie = (ep.getSerie() != null) ? ep.getSerie().getNome() : "Sem Série";
                model.addRow(new Object[] { ep.getId(), ep.getNome(), nomeDaSerie });
            }

            lblResultados.setText("Resultados: " + lista.size() + " episódios cadastrados");
        } catch (Exception erro) {
            lblMensagem.setText("Erro ao listar: " + erro.getMessage());
        }
    }

    private void criarEpisodio() {
        try {
            if (txtNome.getText().isEmpty() || txtSerie.getText().isEmpty()) {
                lblMensagem.setText("O nome do episódio e o nome da série são obrigatórios.");
                return;
            }
            
            String nomeEpisodio = txtNome.getText().trim();
            String nomeSerie = txtSerie.getText().trim();
            
            // Usa o requisito de série para vincular e criar o episódio no banco
            requisitoSerie.adicionarEpisodio(nomeSerie, nomeEpisodio);
            
            lblMensagem.setText("Episódio cadastrado com sucesso!");
            listagem();
            btnLimpar.doClick(); // Limpa os campos após salvar
            
        } catch (Exception ex) {
            lblMensagem.setText("Erro: " + ex.getMessage());
        }
    }

    private void apagarEpisodio() {
        try {
            if (txtId.getText().isEmpty()) {
                lblMensagem.setText("Selecione um episódio na tabela para apagar.");
                return;
            }
            
            int id = Integer.parseInt(txtId.getText());
            String nome = txtNome.getText();
            
            Object[] options = { "Confirmar Exclusão", "Cancelar" };
            int escolha = JOptionPane.showOptionDialog(null, 
                "Deseja realmente apagar o episódio '" + nome + "'?", 
                "Alerta", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, 
                null, options, options[1]);
            
            if (escolha == 0) {
                requisitoEpisodio.excluirEpisodio(id);
                lblMensagem.setText("Episódio excluído com sucesso!");
                listagem();
                btnLimpar.doClick();
            }
        } catch (Exception erro) {
            lblMensagem.setText("Erro ao apagar: " + erro.getMessage());
        }
    }
}