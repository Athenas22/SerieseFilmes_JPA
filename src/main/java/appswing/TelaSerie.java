package appswing;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

import modelo.Serie;
import requisitos.RequisitoSerie;

public class TelaSerie {
    private JDialog frame;
    private JTable table;
    private JScrollPane scrollPane;
    private JButton btnCriar;
    private JButton btnApagar;
    private JButton btnLimpar;
    private JButton btnBuscarFoto;
    private JButton btnLimparFoto;
    private JLabel lblMensagem;
    private JLabel lblResultados;
    private JLabel lblNome;
    private JLabel lblData;
    private JLabel lblInfo;
    private JLabel lblFoto;
    private JTextField txtNome;
    private JTextField txtDataLancamento;
    private JTextField txtInfo;

    private BufferedImage image;
    private RequisitoSerie requisito;

    public TelaSerie() {
        requisito = new RequisitoSerie();
        initialize();
    }

    private void initialize() {
        frame = new JDialog();
        frame.setResizable(false);
        frame.setModal(true);
        frame.setTitle("Gerenciar Séries");
        frame.setBounds(100, 100, 700, 550);
        frame.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        frame.getContentPane().setLayout(null);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent arg0) {
                listagem();
            }
        });

        scrollPane = new JScrollPane();
        scrollPane.setBounds(21, 39, 650, 150);
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
                        String nomeSerie = (String) table.getValueAt(table.getSelectedRow(), 0);
                        
                        // Busca a série na lista para preencher os dados
                        Serie serieSelecionada = null;
                        for (Serie s : requisito.listarTodas()) {
                            if (s.getNome().equals(nomeSerie)) {
                                serieSelecionada = s;
                                break;
                            }
                        }

                        if (serieSelecionada != null) {
                            txtNome.setText(serieSelecionada.getNome());
                            
                            // Formata a data para exibir na tela
                            DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                            txtDataLancamento.setText(serieSelecionada.getDataLancamento().format(fmt));

                            // Listar informações básicas (Gêneros e Episódios)
                            int qtdEps = serieSelecionada.getEpisodios().size();
                            int qtdGen = serieSelecionada.getGeneros().size();
                            txtInfo.setText("Gêneros: " + qtdGen + " | Episódios: " + qtdEps);

                            // Carregar foto
                            // ATENÇÃO: Verifique se você tem o método getFoto() e setFoto() na classe Serie!
                            /* SE TIVER FOTO NA CLASSE, DESCOMENTE ESTE BLOCO:
                            if (serieSelecionada.getFoto() != null) {
                                InputStream in = new ByteArrayInputStream(serieSelecionada.getFoto());
                                image = ImageIO.read(in);
                                ImageIcon icon = new ImageIcon(image.getScaledInstance(lblFoto.getWidth(), lblFoto.getHeight(), Image.SCALE_SMOOTH));
                                lblFoto.setIcon(icon);
                                lblFoto.setText("");
                            } else {
                            */
                                image = null;
                                lblFoto.setIcon(null);
                                lblFoto.setText("Sem foto");
                            /* } */
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
        lblMensagem.setBounds(21, 480, 650, 14);
        frame.getContentPane().add(lblMensagem);

        lblResultados = new JLabel("Selecione uma série para ver os detalhes");
        lblResultados.setBounds(21, 200, 400, 14);
        frame.getContentPane().add(lblResultados);

        lblNome = new JLabel("Nome:");
        lblNome.setHorizontalAlignment(SwingConstants.RIGHT);
        lblNome.setFont(new Font("Tahoma", Font.PLAIN, 11));
        lblNome.setBounds(21, 230, 80, 14);
        frame.getContentPane().add(lblNome);

        lblData = new JLabel("Lançamento:");
        lblData.setHorizontalAlignment(SwingConstants.RIGHT);
        lblData.setFont(new Font("Tahoma", Font.PLAIN, 11));
        lblData.setBounds(21, 260, 80, 14);
        frame.getContentPane().add(lblData);

        lblInfo = new JLabel("Detalhes:");
        lblInfo.setHorizontalAlignment(SwingConstants.RIGHT);
        lblInfo.setFont(new Font("Tahoma", Font.PLAIN, 11));
        lblInfo.setBounds(21, 290, 80, 14);
        frame.getContentPane().add(lblInfo);

        lblFoto = new JLabel("Sem foto");
        lblFoto.setHorizontalAlignment(SwingConstants.CENTER);
        lblFoto.setBorder(new LineBorder(Color.BLACK));
        lblFoto.setBounds(500, 230, 120, 160);
        frame.getContentPane().add(lblFoto);

        txtNome = new JTextField();
        txtNome.setFont(new Font("Tahoma", Font.PLAIN, 12));
        txtNome.setColumns(10);
        txtNome.setBounds(110, 226, 250, 20);
        frame.getContentPane().add(txtNome);

        txtDataLancamento = new JTextField();
        txtDataLancamento.setToolTipText("Ex: 20/05/2015");
        txtDataLancamento.setFont(new Font("Tahoma", Font.PLAIN, 12));
        txtDataLancamento.setColumns(10);
        txtDataLancamento.setBounds(110, 256, 150, 20);
        frame.getContentPane().add(txtDataLancamento);

        txtInfo = new JTextField();
        txtInfo.setEditable(false);
        txtInfo.setFont(new Font("Tahoma", Font.PLAIN, 12));
        txtInfo.setColumns(10);
        txtInfo.setBounds(110, 286, 350, 20);
        frame.getContentPane().add(txtInfo);

        btnCriar = new JButton("Criar Série");
        btnCriar.setBounds(21, 350, 110, 25);
        btnCriar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                criarSerie();
            }
        });
        frame.getContentPane().add(btnCriar);

        btnApagar = new JButton("Apagar Série");
        btnApagar.setBounds(141, 350, 110, 25);
        btnApagar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                apagarSerie();
            }
        });
        frame.getContentPane().add(btnApagar);

        btnLimpar = new JButton("Limpar");
        btnLimpar.setBounds(261, 350, 90, 25);
        btnLimpar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                txtNome.setText("");
                txtDataLancamento.setText("");
                txtInfo.setText("");
                image = null;
                lblFoto.setIcon(null);
                lblFoto.setText("Sem foto");
            }
        });
        frame.getContentPane().add(btnLimpar);

        btnBuscarFoto = new JButton("Buscar Foto");
        btnBuscarFoto.setBounds(500, 400, 120, 25);
        btnBuscarFoto.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                File file = selecionarArquivoFoto();
                if (file == null) return;
                try {
                    image = ImageIO.read(file);
                    ImageIcon icon = new ImageIcon(image.getScaledInstance(lblFoto.getWidth(), lblFoto.getHeight(), Image.SCALE_SMOOTH));
                    lblFoto.setIcon(icon);
                    lblFoto.setText("");
                } catch (IOException ex) {
                    lblMensagem.setText("Erro ao carregar foto: " + ex.getMessage());
                }
            }
        });
        frame.getContentPane().add(btnBuscarFoto);

        btnLimparFoto = new JButton("Limpar Foto");
        btnLimparFoto.setBounds(500, 430, 120, 25);
        btnLimparFoto.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                image = null;
                lblFoto.setIcon(null);
                lblFoto.setText("Sem foto");
            }
        });
        frame.getContentPane().add(btnLimparFoto);

        frame.setVisible(true);
    }

    private void listagem() {
        try {
            DefaultTableModel model = new DefaultTableModel();
            table.setModel(model);

            model.addColumn("Nome da Série");
            model.addColumn("Data Lançamento");
            model.addColumn("Qtd Episódios");

            List<Serie> lista = requisito.listarTodas();
            DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");

            for (Serie s : lista) {
                String dataFormatada = s.getDataLancamento() != null ? s.getDataLancamento().format(fmt) : "N/A";
                model.addRow(new Object[] { s.getNome(), dataFormatada, s.getEpisodios().size() });
            }

            lblResultados.setText("Resultados: " + lista.size() + " séries cadastradas");
        } catch (Exception erro) {
            lblMensagem.setText("Erro ao listar: " + erro.getMessage());
        }
    }

    private void criarSerie() {
        try {
            if (txtNome.getText().isEmpty() || txtDataLancamento.getText().isEmpty()) {
                lblMensagem.setText("Nome e Data de Lançamento são obrigatórios.");
                return;
            }
            
            String nome = txtNome.getText().trim();
            String dataStr = txtDataLancamento.getText().trim();
            
            // Converte a string dd/MM/yyyy para LocalDate
            DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate data = LocalDate.parse(dataStr, fmt);

            byte[] fotoBytes = null;
            if (image != null) {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ImageIO.write(image, "jpg", baos);
                fotoBytes = baos.toByteArray();
            }

            requisito.cadastrarSerie(nome, data, fotoBytes);
            lblMensagem.setText("Série cadastrada com sucesso!");
            listagem();
            
        } catch (Exception ex) {
            lblMensagem.setText("Erro: " + ex.getMessage() + " (Verifique se a data está no formato dd/MM/yyyy)");
        }
    }

    private void apagarSerie() {
        try {
            if (txtNome.getText().isEmpty()) {
                lblMensagem.setText("Selecione uma série primeiro na tabela.");
                return;
            }
            String nome = txtNome.getText();
            Object[] options = { "Confirmar Exclusão", "Cancelar" };
            int escolha = JOptionPane.showOptionDialog(null, 
                "Esta operação apagará a série '" + nome + "' e todos os seus episódios.", 
                "Alerta", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, 
                null, options, options[1]);
            
            if (escolha == 0) {
                requisito.excluirSerie(nome);
                lblMensagem.setText("Série excluída com sucesso!");
                listagem();
                btnLimpar.doClick(); // Limpa os campos após apagar
            }
        } catch (Exception erro) {
            lblMensagem.setText("Erro ao apagar: " + erro.getMessage());
        }
    }

    private File selecionarArquivoFoto() {
        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Imagens", "jpg", "jpeg", "png");
        chooser.setFileFilter(filter);
        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        int result = chooser.showOpenDialog(frame);
        if (result == JFileChooser.APPROVE_OPTION) {
            return chooser.getSelectedFile();
        }
        return null;
    }
}