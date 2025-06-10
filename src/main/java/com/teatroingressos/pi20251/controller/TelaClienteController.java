package com.teatroingressos.pi20251.controller;

import com.teatroingressos.pi20251.app.MainApp;
import com.teatroingressos.pi20251.exception.BaseException;
import com.teatroingressos.pi20251.model.domain.Cliente;
import com.teatroingressos.pi20251.model.domain.Endereco;
import com.teatroingressos.pi20251.model.domain.Ingresso;
import com.teatroingressos.pi20251.service.ClienteService;
import com.teatroingressos.pi20251.util.*;
import javafx.beans.binding.BooleanBinding;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Spinner;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class TelaClienteController implements Initializable {

    @FXML
    private TextField TFcpfCompra;

    @FXML
    private Button btnCadastrarCliente;

    @FXML
    private Button btnComprarIngresso;

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

    @FXML
    void busacarIngrasso(ActionEvent event) {

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
    void cadastroFidelidade(ActionEvent event) {
        apagarCamposCadastro();
        penalCadastro.setVisible(true);
        panelVerIngressos.setVisible(false);
        penelComprarIngresso.setVisible(false);
    }

    @FXML
    void comprarIngresso(ActionEvent event) {
        penalCadastro.setVisible(false);
        panelVerIngressos.setVisible(false);
        penelComprarIngresso.setVisible(true);
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        gerenciarBotaoCadastro();

        inicializarCamposCadastro();

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
}