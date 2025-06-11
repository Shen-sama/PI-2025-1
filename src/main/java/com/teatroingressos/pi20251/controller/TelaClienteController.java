package com.teatroingressos.pi20251.controller;

import com.sun.tools.javac.Main;
import com.teatroingressos.pi20251.app.MainApp;
import com.teatroingressos.pi20251.exception.BaseException;
import com.teatroingressos.pi20251.exception.PersistenciaException;
import com.teatroingressos.pi20251.model.domain.*;
import com.teatroingressos.pi20251.service.ClienteService;
import com.teatroingressos.pi20251.service.IngressoService;
import com.teatroingressos.pi20251.util.*;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class TelaClienteController implements Initializable {

    @FXML
    private Button btnCadastrarCliente;

    @FXML
    private Button btnComprarIngresso;

    @FXML
    private Button btnBuscar;

    @FXML
    private ComboBox<String> cbArea;

    @FXML
    private ComboBox<String> cbEstado;

    @FXML
    private ComboBox<String> cbNomePeca;

    @FXML
    private ComboBox<String> cbSessao;

    @FXML
    private DatePicker dfDataNascimento;

    @FXML
    private GridPane gpPoltrona;

    @FXML
    private AnchorPane panelVerIngressos;

    @FXML
    private AnchorPane penalCadastro;

    @FXML
    private AnchorPane penelComprarIngresso;

    @FXML
    private Spinner<Integer> spQtIngresso;

    @FXML
    private TableColumn<Ingresso, String> tcArea;

    @FXML
    private TableColumn<Ingresso, LocalDate> tcDataCompra;

    @FXML
    private TableColumn<Ingresso, String> tcHorario;

    @FXML
    private TableColumn<Ingresso, String> tcNomePeca;

    @FXML
    private TableColumn<Ingresso, String> tcPoltrona;

    @FXML
    private TableColumn<Ingresso, Double> tcPreco;

    @FXML
    private TextField tfBairro;

    @FXML
    private TextField tfCEP;

    @FXML
    private TextField tfCPFCadastro;

    @FXML
    private TextField tfCPFCompra;

    @FXML
    private TextField tfCidade;

    @FXML
    private TextField tfComplemento;

    @FXML
    private TextField tfCpfPesquisa;

    @FXML
    private TextField tfNome;

    @FXML
    private TextField tfNumero;

    @FXML
    private TextField tfPreco;

    @FXML
    private TextField tfRua;

    @FXML
    private TextField tfTelefone;

    @FXML
    private TableView<Ingresso> tvListaIngressos;

    private ObservableList<String> nomesPecas = FXCollections.observableArrayList();

    private final Map<String, Area> poltronasSelecionadas = new HashMap<>();

    private final IntegerProperty quantidadePoltronasSelecionadas = new SimpleIntegerProperty(0);

    @FXML
    void buscarIngresso(ActionEvent event) {
        if (!ValidadorCPF.isCpfValido(tfCpfPesquisa.getText())) {
            AlertUtils.mostrarErro("Erro na Pesquisa", "CPF inválido.");
            return;
        }

        try {
            IngressoService ingressoService = new IngressoService();
            List<Ingresso> ingressos = ingressoService.buscarPorCpf(tfCpfPesquisa.getText());

            ObservableList<Ingresso> listaIngressos = FXCollections.observableArrayList();
            listaIngressos.setAll(ingressos);

            tvListaIngressos.getItems().clear();
            tvListaIngressos.getItems().addAll(listaIngressos);
        } catch (PersistenciaException ex) {
            AlertUtils.mostrarErro("Erro na Pesquisa", "Falha na busca de ingressos: " + ex.getMessage());
        }
    }

    @FXML
    void cadastrarCliente(ActionEvent event) {
        if (!ValidadorCPF.isCpfValido(tfCPFCadastro.getText())) {
            AlertUtils.mostrarErro("Erro no Cadastro", "CPF inválido.");
            return;
        }

        Cliente clienteRepetido = MainApp.getClienteRepository().getClientesPorCpf().get(tfCPFCadastro.getText());

        if (clienteRepetido != null) {
            AlertUtils.mostrarErro("Erro no Cadastro", "CPF já cadastrado.");
            return;
        }

        if (!DataUtils.validarDataNascimento(dfDataNascimento.getValue(), 18, 120)) {
            AlertUtils.mostrarErro("Erro no Cadastro", "Data de Nascimento inválida.");
            return;
        }

        Endereco endereco = new Endereco();
        endereco.setRua(tfRua.getText());
        endereco.setComplemento(tfComplemento.getText());
        endereco.setNumero(tfNumero.getText());
        endereco.setBairro(tfBairro.getText());
        endereco.setCidade(tfCidade.getText());
        endereco.setEstado(cbEstado.getValue());
        endereco.setCEP(tfCEP.getText());

        Cliente cliente = new Cliente();
        cliente.setNome(tfNome.getText());
        cliente.setCpf(tfCPFCadastro.getText());
        cliente.setTelefone(tfTelefone.getText());
        cliente.setEndereco(endereco);
        cliente.setDataNascimento(dfDataNascimento.getValue());

        try {
            ClienteService clienteService = new ClienteService();
            Cliente clienteSalvo = clienteService.cadastrarCliente(cliente);

            MainApp.getClienteRepository().cadastrar(clienteSalvo);

            AlertUtils.mostrarSucesso("Cliente cadastrado com sucesso!");
            apagarCamposCadastro();

        } catch (BaseException e) {
            AlertUtils.mostrarErro("Erro no Cadastro", e.getMessage());
        }
    }

    @FXML
    void finalizarCompra(ActionEvent event) {
        if (!ValidadorCPF.isCpfValido(tfCPFCompra.getText())) {
            AlertUtils.mostrarErro("Erro na Compra de Ingresso", "CPF inválido.");
            return;
        }

        PecaTeatral pecaSelecionada = MainApp.getPecaTeatralRepository().getPecasPorNome().get(cbNomePeca.getValue());
        String horario = cbSessao.getValue();

        Sessao sessao = encontrarSessaoPorHorario(pecaSelecionada, horario);

        if (sessao == null) {
            AlertUtils.mostrarErro("Erro na Compra de Ingresso", "Sessão não encontrada.");
            return;
        }

        List<Ingresso> ingressosParaSalvar = new ArrayList<>();

        for (Map.Entry<String, Area> entrada : poltronasSelecionadas.entrySet()) {
            String codigoPoltrona = entrada.getKey().split("-")[1];
            Area area = entrada.getValue();

            Poltrona poltrona = area.getPoltronas().get(codigoPoltrona);

            Ingresso ingresso = new Ingresso();
            ingresso.setCpf(tfCPFCompra.getText());
            ingresso.setNomePeca(pecaSelecionada.getNome());
            ingresso.setHoraInicio(sessao.getHorario());
            ingresso.setAreaPoltrona(area.getTipo().toString());
            ingresso.setCodPoltrona(poltrona.getCodigo());
            ingresso.setPreco(area.getPreco());
            ingresso.setDataCompra(LocalDate.now());
            ingresso.setIdSessao(sessao.getId());

            Cliente clienteCadastrado = MainApp.getClienteRepository().getClientesPorCpf().get(tfCPFCompra.getText());

            if (clienteCadastrado != null) {
                ingresso.setIdCliente(clienteCadastrado.getId());
            }

            ingressosParaSalvar.add(ingresso);
        }

        try {
            IngressoService ingressoService = new IngressoService();

            for (Ingresso ingresso : ingressosParaSalvar) {
                ingressoService.comprarIngresso(ingresso, sessao);
            }

            AlertUtils.mostrarSucesso("Compra realizada com sucesso!");
            apagarCamposCompra();

        } catch (Exception ex) {
            AlertUtils.mostrarErro("Erro na Compra de Ingresso", "Erro ao salvar ingressos: " + ex.getMessage());
        }
    }

    @FXML
    void cadastroFidelidade(ActionEvent event) {
        apagarCamposCadastro();
        penalCadastro.setVisible(true);
        panelVerIngressos.setVisible(false);
        penelComprarIngresso.setVisible(false);
        apagarCamposPesquisa();
    }

    @FXML
    void comprarIngresso(ActionEvent event) {
        penalCadastro.setVisible(false);
        panelVerIngressos.setVisible(false);
        penelComprarIngresso.setVisible(true);
        apagarCamposPesquisa();
    }

    @FXML
    void verIngresso(ActionEvent event) {
        penalCadastro.setVisible(false);
        panelVerIngressos.setVisible(true);
        penelComprarIngresso.setVisible(false);
    }

    @FXML
    void voltarMenuInicial(ActionEvent event) throws IOException {
        SceneSwap.setRoot(MainApp.getScene(), "telaInicial");
    }

    @FXML
    void atualizarComboBoxSessao(ActionEvent event) {
        String nomeSelecionado = cbNomePeca.getValue();

        PecaTeatral peca = MainApp.getPecaTeatralRepository().getPecasPorNome().get(nomeSelecionado);
        if (peca != null) {

            List<String> horariosDisponiveis = peca.getSessoes().stream()
                    .filter(Sessao::isDisponivel)
                    .map(Sessao::getHorario)
                    .toList();


            cbSessao.getItems().setAll(horariosDisponiveis);
        } else {
            cbSessao.getItems().clear();
        }

        poltronasSelecionadas.clear();
        atualizarGridPoltronas();
    }

    @FXML
    void atualizarComboBoxArea(ActionEvent event) {
        cbArea.getItems().clear();
        String nomeSelecionado = cbNomePeca.getValue();
        String horarioSelecionada = cbSessao.getValue();

        PecaTeatral peca = MainApp.getPecaTeatralRepository().getPecasPorNome().get(nomeSelecionado);
        List<Sessao> sessoes = peca.getSessoes();

        for (Sessao sessao : sessoes) {
            if (horarioSelecionada.equalsIgnoreCase(sessao.getHorario())) {
                List<Area> areas = sessao.getSala().getAreas();

                for (Area area : areas) {
                    cbArea.getItems().add(area.getTipo().toString());
                }
                break;
            }
        }

        poltronasSelecionadas.clear();
        atualizarGridPoltronas();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        gerenciarBotaoCadastro();
        gerenciarBotaoComprar();
        gerenciarBotaoBuscar();

        inicializarCamposCadastro();
        inicializarCamposCompraIngresso();
        inicializarCamposVerIngresso();

    }

    private void apagarCamposCadastro() {
        tfNome.clear();
        tfComplemento.clear();
        tfNumero.clear();
        tfCPFCadastro.clear();
        tfTelefone.clear();
        dfDataNascimento.setValue(null);
        tfRua.clear();
        tfBairro.clear();
        tfCEP.clear();
        tfCidade.clear();
        cbEstado.setValue(null);
    }

    private void apagarCamposCompra() {
        tfCPFCompra.clear();
        tfPreco.setText("0,00");

        cbArea.getSelectionModel().clearSelection();
        cbSessao.getSelectionModel().clearSelection();
        cbNomePeca.getSelectionModel().clearSelection();

        spQtIngresso.getValueFactory().setValue(1);

        poltronasSelecionadas.clear();
        atualizarGridPoltronas();
        atualizarPrecoTotal();

    }

    private void apagarCamposPesquisa() {
        tfCpfPesquisa.clear();

        tvListaIngressos.getItems().clear();
    }

    private void inicializarCamposCadastro() {
        TextFieldLimiter.limitarPorRegex(tfNome, TextFieldLimiter.getNamePattern());
        TextFieldLimiter.limitarNumeros(tfNumero, 3);
        TextFieldLimiter.limitarPorRegex(tfCidade, TextFieldLimiter.getCityPattern());
        TextFieldLimiter.limitarNumerosFormatado(tfCPFCadastro, "CPF", 11);
        TextFieldHighlight.aplicarHighlightComLimite(tfCPFCadastro, 14);
        TextFieldLimiter.limitarNumerosFormatado(tfTelefone, "Telefone", 11);
        TextFieldHighlight.aplicarHighlightComLimite(tfTelefone, 15);
        TextFieldLimiter.limitarNumerosFormatado(tfCEP, "CEP", 8);
        TextFieldHighlight.aplicarHighlightComLimite(tfCEP, 9);
        cbEstado.getItems().clear();
        cbEstado.getItems().addAll("AC", "AL", "AP", "AM", "BA", "CE", "DF", "ES", "GO", "MA", "MT", "MS", "MG", "PA", "PB", "PR", "PE", "PI", "RJ", "RN", "RS",
                "RO", "RR", "SC", "SP", "SE", "TO");
    }

    private void inicializarCamposCompraIngresso() {
        TextFieldLimiter.limitarNumerosFormatado(tfCPFCompra, "CPF", 11);
        TextFieldHighlight.aplicarHighlightComLimite(tfCPFCompra, 14);

        tfPreco.setText("0,00");

        Map<String, PecaTeatral> pecas = MainApp.getPecaTeatralRepository().getPecasPorNome();
        nomesPecas.setAll(
                pecas.values().stream().filter(PecaTeatral::verificarDisponibilidade).map(PecaTeatral::getNome).toList()
        );
        cbNomePeca.setItems(nomesPecas);

        spQtIngresso.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, Integer.MAX_VALUE, 1));

        spQtIngresso.valueProperty().addListener((obs, oldVal, newVal) -> {

            while (poltronasSelecionadas.size() > newVal) {
                String chaveRemovida = poltronasSelecionadas.keySet().iterator().next();
                poltronasSelecionadas.remove(chaveRemovida);
            }
            atualizarPrecoTotal();
            atualizarGridPoltronas();
        });

        cbArea.setOnAction(event -> atualizarGridPoltronas());
    }

    private void inicializarCamposVerIngresso() {
        TextFieldLimiter.limitarNumerosFormatado(tfCpfPesquisa, "CPF", 11);
        TextFieldHighlight.aplicarHighlightComLimite(tfCpfPesquisa, 14);

        tcNomePeca.setCellValueFactory(new PropertyValueFactory<>("nomePeca"));
        tcDataCompra.setCellValueFactory(new PropertyValueFactory<>("dataCompra"));
        tcHorario.setCellValueFactory(new PropertyValueFactory<>("horaInicio"));
        tcArea.setCellValueFactory(new PropertyValueFactory<>("areaPoltrona"));
        tcPoltrona.setCellValueFactory(new PropertyValueFactory<>("codPoltrona"));
        tcPreco.setCellValueFactory(new PropertyValueFactory<>("preco"));

        tcPreco.setCellFactory(tc -> new TableCell<Ingresso, Double>()
        {
            @Override
            protected void updateItem(Double preco, boolean vazio)
            {
                super.updateItem(preco, vazio);
                if (vazio)
                {
                    setText(null);
                }
                else
                {
                    setText(String.format("R$ %.2f", preco));
                }
            }
        });

        tcDataCompra.setCellFactory(tc -> new TableCell<Ingresso, LocalDate>() {
            private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

            @Override
            protected void updateItem(LocalDate item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null : formatter.format(item));
            }
        });
    }

    private void gerenciarBotaoCadastro()
    {
        BooleanBinding bb = new BooleanBinding()
        {
            {
                super.bind(tfNome.textProperty(), tfCPFCadastro.textProperty(), tfTelefone.textProperty(), dfDataNascimento.valueProperty(), tfRua.textProperty(),
                        tfComplemento.textProperty(), tfNumero.textProperty(), tfBairro.textProperty(), tfCidade.textProperty(), tfCEP.textProperty(),
                        cbEstado.valueProperty());
            }

            @Override
            protected boolean computeValue()
            {
                return (tfNome.getText().isEmpty() || tfCPFCadastro.getText().length() < 14 || tfTelefone.getText().length() < 15 ||
                        dfDataNascimento.getValue() == null || tfRua.getText().isEmpty() || tfComplemento.getText().isEmpty() ||
                        tfNumero.getText().isEmpty() || tfBairro.getText().isEmpty() || tfCidade.getText().isEmpty() || tfCEP.getText().length() < 9 ||
                        cbEstado.getValue() == null);
            }
        };

        btnCadastrarCliente.disableProperty().bind(bb);
    }

    private void gerenciarBotaoComprar() {
        BooleanBinding compraInvalida = new BooleanBinding() {
            {
                super.bind(tfCPFCompra.textProperty(), cbNomePeca.valueProperty(), cbSessao.valueProperty(), cbArea.valueProperty(),
                        spQtIngresso.valueProperty(), quantidadePoltronasSelecionadas);
            }

            @Override
            protected boolean computeValue() {
                return cbNomePeca.getValue() == null
                        || tfCPFCompra.getText().length() < 14
                        || cbSessao.getValue() == null
                        || cbArea.getValue() == null
                        || quantidadePoltronasSelecionadas.get() != spQtIngresso.getValue();
            }
        };

        btnComprarIngresso.disableProperty().bind(compraInvalida);
    }

    private void gerenciarBotaoBuscar()
    {
        BooleanBinding bb = new BooleanBinding()
        {
            {
                super.bind(tfCpfPesquisa.textProperty());
            }

            @Override
            protected boolean computeValue()
            {
                return (tfCpfPesquisa.getText().length() < 14);
            }
        };

        btnBuscar.disableProperty().bind(bb);
    }

    private void atualizarGridPoltronas() {
        gpPoltrona.getChildren().clear();

        String nomePeca = cbNomePeca.getValue();
        String horario = cbSessao.getValue();
        String nomeArea = cbArea.getValue();

        if (nomePeca == null || horario == null || nomeArea == null) return;

        PecaTeatral peca = MainApp.getPecaTeatralRepository().getPecasPorNome().get(nomePeca);
        Sessao sessao = peca.getSessoes().stream()
                .filter(s -> s.getHorario().equals(horario))
                .findFirst().orElse(null);

        if (sessao == null) return;

        Area area = sessao.getSala().getAreaPorNome(nomeArea);
        if (area == null) return;

        Map<String, Poltrona> mapaPoltronas = area.getPoltronas();

        List<Poltrona> poltronasOrdenadas = mapaPoltronas.values().stream()
                .sorted(Comparator.comparing((Poltrona p) -> p.getCodigo().substring(0,1))
                .thenComparing(p -> Integer.parseInt(p.getCodigo().substring(1))))
                .toList();

        int colunas = area.getPoltronasPorFileira();

        gpPoltrona.getColumnConstraints().clear();
        gpPoltrona.getRowConstraints().clear();
        gpPoltrona.setHgap(10);
        gpPoltrona.setVgap(10);
        gpPoltrona.setPadding(new Insets(10));

        for (int col = 0; col < colunas; col++) {
            ColumnConstraints cc = new ColumnConstraints();
            cc.setHgrow(Priority.ALWAYS);
            cc.setFillWidth(true);
            gpPoltrona.getColumnConstraints().add(cc);
        }

        int linhas = (int) Math.ceil((double) poltronasOrdenadas.size() / colunas);
        for (int row = 0; row < linhas; row++) {
            RowConstraints rc = new RowConstraints();
            rc.setVgrow(Priority.ALWAYS);
            rc.setFillHeight(true);
            gpPoltrona.getRowConstraints().add(rc);
        }

        for (int i = 0; i < poltronasOrdenadas.size(); i++) {
            Poltrona p = poltronasOrdenadas.get(i);
            String chave = area.getTipo().toString() + "-" + p.getCodigo();
            Button btn = new Button(p.getCodigo());
            btn.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

            if (poltronasSelecionadas.containsKey(chave)) {
                btn.setStyle("-fx-background-color: green;");
            }

            if (!p.isDisponivel()) {
                btn.setDisable(true);
                btn.setStyle("-fx-background-color: #d9534f; -fx-text-fill: white;");
            }

            btn.setOnAction(e -> selecionarPoltrona(btn, area, chave));

            int coluna = i % colunas;
            int linha = i / colunas;

            gpPoltrona.add(btn, coluna, linha);
        }
    }

    private void selecionarPoltrona(Button btn, Area areaSelecionada, String chave) {

        if (poltronasSelecionadas.containsKey(chave)) {
            poltronasSelecionadas.remove(chave);
            btn.setStyle("");
        } else {
            if (poltronasSelecionadas.size() < spQtIngresso.getValue()) {
                poltronasSelecionadas.put(chave, areaSelecionada);
                btn.setStyle("-fx-background-color: green;");
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Aviso");
                alert.setHeaderText("Limite de seleção");
                alert.setContentText("Você já selecionou o número máximo de poltronas.");
                alert.showAndWait();
            }
        }

        System.out.println(chave);

        quantidadePoltronasSelecionadas.set(poltronasSelecionadas.size());
        atualizarPrecoTotal();
    }

    private void atualizarPrecoTotal() {
        double total = poltronasSelecionadas.values().stream()
                .mapToDouble(Area::getPreco)
                .sum();

        tfPreco.setText(String.format("%.2f", total));
    }

    private Sessao encontrarSessaoPorHorario(PecaTeatral peca, String horario) {
        return peca.getSessoes().stream()
                .filter(s -> s.getHorario().equals(horario))
                .findFirst()
                .orElse(null);
    }
}