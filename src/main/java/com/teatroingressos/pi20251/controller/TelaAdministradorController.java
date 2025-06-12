package com.teatroingressos.pi20251.controller;

import com.sun.tools.javac.Main;
import com.teatroingressos.pi20251.app.MainApp;
import com.teatroingressos.pi20251.exception.BaseException;
import com.teatroingressos.pi20251.exception.PecaException;
import com.teatroingressos.pi20251.exception.SessaoException;
import com.teatroingressos.pi20251.model.dao.SessaoDAO;
import com.teatroingressos.pi20251.model.domain.*;
import com.teatroingressos.pi20251.service.PecaTeatralService;
import com.teatroingressos.pi20251.util.AlertUtils;
import com.teatroingressos.pi20251.util.SceneSwap;
import javafx.application.Platform;
import javafx.beans.binding.BooleanBinding;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

public class TelaAdministradorController implements Initializable {

    @FXML
    private Button btnCadastrarPeca;

    @FXML
    private Button btnCadastrarSessao;

    @FXML
    private Button btnDesabilitarSessao;

    @FXML
    private Button btnHabilitarSessao;

    @FXML
    private ComboBox<String> cbDuracaoHora;

    @FXML
    private ComboBox<String> cbDuracaoMin;

    @FXML
    private ComboBox<String> cbHoraSessao;

    @FXML
    private ComboBox<String> cbHorarioPeca;

    @FXML
    private ComboBox<String> cbMinSessao;

    @FXML
    private ComboBox<String> cbNomePeca;

    @FXML
    private ComboBox<String> cbNomePecaRemover;

    @FXML
    private BarChart<String, Number> chartEstatisticas;

    @FXML
    private ToggleGroup estatistica;

    @FXML
    private AnchorPane panelAdicionarPeca;

    @FXML
    private AnchorPane panelEstatistica;

    @FXML
    private AnchorPane panelRemoverPeca;

    @FXML
    private AnchorPane panelListaCleinte;

    @FXML
    private RadioButton radioFaturamento;

    @FXML
    private RadioButton radioIngressosPeca;

    @FXML
    private RadioButton radioOcupacaoPoltronas;

    @FXML
    private TextArea taSinopse;

    @FXML
    private TextField tfFaturamentoMedio;

    @FXML
    private TextField tfNomePeca;

    @FXML
    private TableColumn<Cliente, Long> tcCodigoCliente;

    @FXML
    private TableColumn<Cliente, String> tcCpfCliente;

    @FXML
    private TableColumn<Cliente, String> tcNomeCliente;

    @FXML
    private TableColumn<Cliente, String> tcTelefoneCliente;

    @FXML
    private TableView<Cliente> tvListaClientes;

    private ObservableList<String> nomesPecas = FXCollections.observableArrayList();

    @FXML
    void cadastrarPeca(ActionEvent event) throws PecaException {
        PecaTeatral pecaTeatral = new PecaTeatral();
        pecaTeatral.setNome(tfNomePeca.getText());
        pecaTeatral.setSinopse(taSinopse.getText());
        pecaTeatral.setDuracao(cbDuracaoHora.getValue() + "h" + cbDuracaoMin.getValue());

        try {
            PecaTeatralService pecaTeatralService = new PecaTeatralService();
            PecaTeatral pecaSalva = pecaTeatralService.cadastrarPeca(pecaTeatral);

            MainApp.getPecaTeatralRepository().cadastrar(pecaSalva);

            AlertUtils.mostrarSucesso("Peça adicionada com sucesso!");
            atualizarComboBoxNomePecas();
            apagarCamposAdicionarPeca();
        } catch (BaseException e) {
            AlertUtils.mostrarErro("Erro no Cadastro", e.getMessage());
        }
    }

    @FXML
    void cadastrarSessao(ActionEvent event) throws SessaoException {
        PecaTeatral pecaEscolhida = MainApp.getPecaTeatralRepository().getPecasPorNome().get(cbNomePeca.getValue());

        Sessao sessao = new Sessao();
        sessao.setHorario(cbHoraSessao.getValue() + ":" + cbMinSessao.getValue());

        try {
            PecaTeatralService pecaTeatralService = new PecaTeatralService();
            pecaTeatralService.adicionarSessao(pecaEscolhida, sessao);

            AlertUtils.mostrarSucesso("Sessão adicionada com sucesso!");
            apagarCamposAdicionarSessao();
        } catch (BaseException e) {
            AlertUtils.mostrarErro("Erro no Cadastro", e.getMessage());
        }
    }

    @FXML
    void desabilitarSessao(ActionEvent event) {
        SessaoDAO sessaoDAO = new SessaoDAO();

        try {
            sessaoDAO.atualizarDisponibilidade(cbNomePecaRemover.getValue(), cbHorarioPeca.getValue(), false);
            AlertUtils.mostrarInfo("Sucesso", "Sessão desabilitada com sucesso.");
        } catch (SessaoException e) {
            AlertUtils.mostrarErro("Erro ao atualizar sessão", e.getMessage());
        }

        apagarCamposRemoverPeca();
    }

    @FXML
    void entrarAdicionarPeca(ActionEvent event) {
        panelAdicionarPeca.setVisible(true);
        panelRemoverPeca.setVisible(false);
        panelEstatistica.setVisible(false);
        panelListaCleinte.setVisible(false);
    }

    @FXML
    void entrarEstatistica(ActionEvent event) {
        panelAdicionarPeca.setVisible(false);
        panelRemoverPeca.setVisible(false);
        panelEstatistica.setVisible(true);
        panelListaCleinte.setVisible(false);
    }

    @FXML
    void entrarRemoverPeca(ActionEvent event) {
        panelAdicionarPeca.setVisible(false);
        panelRemoverPeca.setVisible(true);
        panelEstatistica.setVisible(false);
        panelListaCleinte.setVisible(false);
    }

    @FXML
    void mostrarListaCliente(ActionEvent event) {
        panelAdicionarPeca.setVisible(false);
        panelRemoverPeca.setVisible(false);
        panelEstatistica.setVisible(false);
        panelListaCleinte.setVisible(true);
    }

    @FXML
    void habilitarSessao(ActionEvent event) {
        SessaoDAO sessaoDAO = new SessaoDAO();

        try {
            sessaoDAO.atualizarDisponibilidade(cbNomePecaRemover.getValue(), cbHorarioPeca.getValue(), true);
            AlertUtils.mostrarInfo("Sucesso", "Sessão habilitada com sucesso.");
        } catch (SessaoException e) {
            AlertUtils.mostrarErro("Erro ao atualizar sessão", e.getMessage());
        }

        apagarCamposRemoverPeca();
    }

    @FXML
    void voltarMenuInicial(ActionEvent event) throws IOException {
        SceneSwap.setRoot(MainApp.getScene(), "telaInicial");
    }

    @FXML
    void atualizarComboBoxHorarioPeca(ActionEvent event) {
        String nomeSelecionado = cbNomePecaRemover.getValue();

        PecaTeatral peca = MainApp.getPecaTeatralRepository().getPecasPorNome().get(nomeSelecionado);
        if (peca != null) {

            List<String> horariosDisponiveis = peca.getSessoes().stream()
                    .map(Sessao::getHorario)
                    .toList();


            cbHorarioPeca.getItems().setAll(horariosDisponiveis);
        } else {
            cbHorarioPeca.getItems().clear();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        gerenciarBotaoCadastroPeca();
        gerenciarBotaoCadastroSessao();
        gerenciarBotoesHabilitarDesabilitarSessao();
        inicializarCamposAdicionarPeca();
        inicializarCamposAdicionarSessao();
        inicializarCamposRemoverPeca();
        inicializarListaClientes();
        inicializarRadioEstatistica();
    }

    private void inicializarListaClientes() {
        ObservableList<Cliente> listaClientes = FXCollections.observableArrayList();

        listaClientes.setAll(MainApp.getClienteRepository().getClientesPorCpf().values());

        tcCodigoCliente.setCellValueFactory(new PropertyValueFactory<>("id"));
        tcNomeCliente.setCellValueFactory(new PropertyValueFactory<>("nome"));
        tcCpfCliente.setCellValueFactory(new PropertyValueFactory<>("cpf"));
        tcTelefoneCliente.setCellValueFactory(new PropertyValueFactory<>("telefone"));

        tvListaClientes.setItems(listaClientes);
    }

    private void inicializarCamposAdicionarPeca() {
        cbDuracaoHora.getItems().clear();
        for (int i = 0; i < 24; i++) {
            cbDuracaoHora.getItems().add(String.format("%02d", i));
        }

        cbDuracaoMin.getItems().clear();
        for (int i = 0; i < 60; i++) {
            cbDuracaoMin.getItems().add(String.format("%02d", i));
        }
    }

    private void inicializarRadioEstatistica() {
        estatistica.selectedToggleProperty().addListener((obs, oldToggle, newToggle) -> {
            if (newToggle == radioIngressosPeca) {
                mostrarIngressosPorPeca();
            } else if (newToggle == radioOcupacaoPoltronas) {
                mostrarOcupacaoPorSessao();
            } else if (newToggle == radioFaturamento) {
                mostrarFaturamento();
            }
        });

        CategoryAxis xAxis = (CategoryAxis) chartEstatisticas.getXAxis();
        xAxis.setTickLabelsVisible(false);
        xAxis.setTickMarkVisible(false);

        mostrarFaturamentoMedio();
    }

    private void apagarCamposAdicionarPeca() {
        tfNomePeca.clear();
        taSinopse.clear();
        cbDuracaoHora.setValue(null);
        cbDuracaoMin.setValue(null);
    }

    private void inicializarCamposAdicionarSessao() {
        cbNomePeca.setItems(nomesPecas);
        atualizarComboBoxNomePecas();

        cbHoraSessao.getItems().clear();
        for (int i = 0; i < 24; i++) {
            cbHoraSessao.getItems().add(String.format("%02d", i));
        }

        cbMinSessao.getItems().clear();
        for (int i = 0; i < 60; i++) {
            cbMinSessao.getItems().add(String.format("%02d", i));
        }
    }

    public void apagarCamposAdicionarSessao() {
        cbNomePeca.setValue(null);
        cbHoraSessao.setValue(null);
        cbMinSessao.setValue(null);
    }

    public void inicializarCamposRemoverPeca() {
        cbNomePecaRemover.setItems(nomesPecas);
    }

    public void apagarCamposRemoverPeca() {
        cbNomePecaRemover.setValue(null);
        cbHorarioPeca.setValue(null);
    }

    private void gerenciarBotaoCadastroPeca() {
        BooleanBinding bb = new BooleanBinding()
        {
            {
                super.bind(tfNomePeca.textProperty(), taSinopse.textProperty(), cbDuracaoHora.valueProperty(), cbDuracaoMin.valueProperty());
            }

            @Override
            protected boolean computeValue()
            {
                return (tfNomePeca.getText().isEmpty() || taSinopse.getText().isEmpty()  || cbDuracaoHora.getValue() == null || cbDuracaoMin.getValue() == null);
            }
        };

        btnCadastrarPeca.disableProperty().bind(bb);
    }

    private void gerenciarBotaoCadastroSessao() {
        BooleanBinding bb = new BooleanBinding()
        {
            {
                super.bind(cbNomePeca.valueProperty(), cbHoraSessao.valueProperty(), cbMinSessao.valueProperty());
            }

            @Override
            protected boolean computeValue()
            {
                return (cbNomePeca.getValue() == null || cbHoraSessao.getValue() == null || cbMinSessao.getValue() == null);
            }
        };

        btnCadastrarSessao.disableProperty().bind(bb);
    }

    private void gerenciarBotoesHabilitarDesabilitarSessao() {
        BooleanBinding bb = new BooleanBinding()
        {
            {
                super.bind(cbNomePecaRemover.valueProperty(), cbHorarioPeca.valueProperty());
            }

            @Override
            protected boolean computeValue()
            {
                return (cbNomePecaRemover.getValue() == null || cbHorarioPeca.getValue() == null);
            }
        };

        btnHabilitarSessao.disableProperty().bind(bb);
        btnDesabilitarSessao.disableProperty().bind(bb);
    }

    private void atualizarComboBoxNomePecas() {
        Map<String, PecaTeatral> pecas = MainApp.getPecaTeatralRepository().getPecasPorNome();
        nomesPecas.setAll(
                pecas.values().stream().map(PecaTeatral::getNome).toList()
        );
    }

    private void mostrarIngressosPorPeca() {
        chartEstatisticas.getData().clear();
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Ingressos por Peça");

        for (PecaTeatral peca : MainApp.getPecaTeatralRepository().getPecasPorNome().values()) {
            int total = 0;
            for (Sessao sessao : peca.getSessoes()) {
                total += sessao.getIngressos().size();
            }

            XYChart.Data<String, Number> data = new XYChart.Data<>(peca.getNome(), total);
            series.getData().add(data);
        }

        chartEstatisticas.getData().add(series);

        for (XYChart.Data<String, Number> data : series.getData()) {
            Platform.runLater(() -> {
                Tooltip tooltip = new Tooltip(data.getXValue() + ": " + data.getYValue() + " ingresso(s)");
                Tooltip.install(data.getNode(), tooltip);
            });
        }
    }

    private void mostrarOcupacaoPorSessao() {
        chartEstatisticas.getData().clear();
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Ocupação por Sessão");

        for (PecaTeatral peca : MainApp.getPecaTeatralRepository().getPecasPorNome().values()) {
            for (Sessao sessao : peca.getSessoes()) {
                int ocupadas = 0;
                int total = 0;
                for (Area area : sessao.getSala().getAreas()) {
                    for (Poltrona p : area.getPoltronas().values()) {
                        if (!p.isDisponivel()) ocupadas++;
                        total++;
                    }
                }
                String nomeSessao = peca.getNome() + " - " + sessao.getHorario();
                double ocupacao = (total == 0) ? 0 : (ocupadas * 100.0) / total;

                XYChart.Data<String, Number> data = new XYChart.Data<>(nomeSessao, ocupacao);
                series.getData().add(data);
            }
        }

        chartEstatisticas.getData().add(series);

        for (XYChart.Data<String, Number> data : series.getData()) {
            Platform.runLater(() -> {
                String texto = String.format("%s\n%.1f%% ocupação", data.getXValue(), data.getYValue().doubleValue());
                Tooltip tooltip = new Tooltip(texto);
                Tooltip.install(data.getNode(), tooltip);
            });
        }
    }

    private void mostrarFaturamento() {
        chartEstatisticas.getData().clear();
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Faturamento");

        for (PecaTeatral peca : MainApp.getPecaTeatralRepository().getPecasPorNome().values()) {
            double total = 0.0;
            for (Sessao sessao : peca.getSessoes()) {
                for (Ingresso ingresso : sessao.getIngressos().values()) {
                    total += ingresso.getPreco();
                }
            }

            XYChart.Data<String, Number> data = new XYChart.Data<>(peca.getNome(), total);
            series.getData().add(data);
        }

        chartEstatisticas.getData().add(series);

        for (XYChart.Data<String, Number> data : series.getData()) {
            Platform.runLater(() -> {
                String texto = String.format("%s\nR$ %.2f", data.getXValue(), data.getYValue().doubleValue());
                Tooltip tooltip = new Tooltip(texto);
                Tooltip.install(data.getNode(), tooltip);
            });
        }
    }

    private void mostrarFaturamentoMedio() {

        int totalSessoes = 0;
        double faturamentoTotal = 0;

        for (PecaTeatral peca : MainApp.getPecaTeatralRepository().getPecasPorNome().values()) {
            for (Sessao sessao : peca.getSessoes()) {
                totalSessoes++;
                for (Ingresso ingresso : sessao.getIngressos().values()) {
                    faturamentoTotal += ingresso.getPreco();
                }
            }
        }

        if (totalSessoes == 0) {
            tfFaturamentoMedio.setText("0,00");
            return;
        }

        double media = faturamentoTotal / totalSessoes;
        tfFaturamentoMedio.setText(String.format("%.2f", media));
    }
}
