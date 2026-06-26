package appswing;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import modelo.Serie;
import requisitos.RequisitoSerie;

public class TelaConsulta {
    private JDialog frame;
    private JTable table;
    private JScrollPane scrollPane;
    private JComboBox<String> cbConsultas;
    private JTextField txtParametro;
    private JButton btnConsultar;
    private JLabel lblMensagem;
    private JLabel lblResultados;
    private JLabel lblParametro;

    private RequisitoSerie requisitoSerie;

    public TelaConsulta() {
        requisitoSerie = new RequisitoSerie();
        initialize();
    }

    private void initialize() {
        frame = new JDialog();
        frame.setResizable(false);
        frame.setModal(true);
        frame.setTitle("Consultas JPQL");
        frame.setBounds(100, 100, 700, 500);
        frame.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        // --- COMPONENTES DO TOPO (Filtros) ---
        String[] opcoes = {
            "1. Séries lançadas no ano X",
            "2. Séries do gênero de nome X",
            "3. Séries com mais de N episódios"
        };
        cbConsultas = new JComboBox<>(opcoes);
        cbConsultas.setBounds(21, 20, 260, 25);
        frame.getContentPane().add(cbConsultas);

        lblParametro = new JLabel("Parâmetro:");
        lblParametro.setHorizontalAlignment(SwingConstants.RIGHT);
        lblParametro.setBounds(290, 25, 70, 14);
        frame.getContentPane().add(lblParametro);

        txtParametro = new JTextField();
        txtParametro.setBounds(370, 22, 130, 22);
        frame.getContentPane().add(txtParametro);

        btnConsultar = new JButton("Consultar");
        btnConsultar.setBounds(520, 21, 100, 25);
        btnConsultar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                executarConsulta();
            }
        });
        frame.getContentPane().add(btnConsultar);

        // --- TABELA DE RESULTADOS ---
        scrollPane = new JScrollPane();
        scrollPane.setBounds(21, 60, 640, 350);
        frame.getContentPane().add(scrollPane);

        table = new JTable() {
            public boolean isCellEditable(int rowIndex, int vColIndex) {
                return false;
            }
        };

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

        // --- RODAPÉ ---
        lblResultados = new JLabel("Resultados: 0");
        lblResultados.setBounds(21, 420, 200, 14);
        frame.getContentPane().add(lblResultados);

        lblMensagem = new JLabel("");
        lblMensagem.setForeground(Color.RED);
        lblMensagem.setBounds(250, 420, 410, 14);
        frame.getContentPane().add(lblMensagem);

        // Inicializa a tabela vazia
        limparTabela();

        frame.setVisible(true);
    }

    private void executarConsulta() {
        lblMensagem.setText("");
        String parametro = txtParametro.getText().trim();
        int tipoConsulta = cbConsultas.getSelectedIndex();

        if (parametro.isEmpty()) {
            lblMensagem.setText("Por favor, digite um parâmetro para a busca.");
            return;
        }

        try {
            List<Serie> listaResultados = null;

            switch (tipoConsulta) {
                case 0: // Ano
                    int ano = Integer.parseInt(parametro);
                    listaResultados = requisitoSerie.consultarPorAno(ano);
                    break;
                case 1: // Gênero
                    listaResultados = requisitoSerie.consultarPorGenero(parametro);
                    break;
                case 2: // Quantidade de Episódios
                    int qtdEpisodios = Integer.parseInt(parametro);
                    listaResultados = requisitoSerie.consultarComMaisDeNEpisodios(qtdEpisodios);
                    break;
            }

            preencherTabela(listaResultados);

        } catch (NumberFormatException ex) {
            lblMensagem.setText("Para esta consulta, o parâmetro deve ser um número inteiro.");
        } catch (Exception ex) {
            lblMensagem.setText("Erro ao consultar: " + ex.getMessage());
        }
    }

    private void preencherTabela(List<Serie> lista) {
        DefaultTableModel model = new DefaultTableModel();
        table.setModel(model);

        model.addColumn("Nome da Série");
        model.addColumn("Data Lançamento");
        model.addColumn("Qtd Episódios");
        model.addColumn("Gêneros");

        if (lista != null && !lista.isEmpty()) {
            DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");

            for (Serie s : lista) {
                String dataFormatada = s.getDataLancamento() != null ? s.getDataLancamento().format(fmt) : "N/A";
                int qtdEps = s.getEpisodios().size();
                int qtdGen = s.getGeneros().size();
                
                model.addRow(new Object[] { s.getNome(), dataFormatada, qtdEps, qtdGen + " gênero(s)" });
            }
            lblResultados.setText("Resultados: " + lista.size() + " séries encontradas");
        } else {
            lblResultados.setText("Resultados: 0");
            lblMensagem.setText("Nenhuma série encontrada com este parâmetro.");
        }
    }

    private void limparTabela() {
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Nome da Série");
        model.addColumn("Data Lançamento");
        model.addColumn("Qtd Episódios");
        model.addColumn("Gêneros");
        table.setModel(model);
    }
}