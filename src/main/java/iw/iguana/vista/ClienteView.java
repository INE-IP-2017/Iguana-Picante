package iw.iguana.vista;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.shared.ui.ValueChangeMode;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.ListSelect;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import iw.iguana.controlador.ClienteEditor;
import iw.iguana.dao.ClienteRepository;
import iw.iguana.modelo.Cliente;


@SpringView(name = ClienteView.VIEW_NAME)
public class ClienteView extends VerticalLayout implements View {
			    
		private static final long serialVersionUID = 1L;

		public static final String VIEW_NAME = "cliente";
		private final ClienteRepository repo;
		private final ClienteEditor editor;
		final Grid<Cliente> grid;
		final TextField nombre, direccion, telefono;


		@Autowired
		public ClienteView(ClienteRepository repo, ClienteEditor editor) {
			 this.repo = repo;
			 this.editor = editor;
			 this.grid = new Grid<>(Cliente.class);
			 this.nombre = new TextField();
			 this.direccion = new TextField();
			 this.telefono = new TextField();
		}
			  
		@PostConstruct
		void init() {
			  
			HorizontalLayout cajas2 = new HorizontalLayout(precio, iva);
			VerticalLayout cajas = new VerticalLayout(nombre, descripcion, cajas2, stock);
			VerticalLayout listas1 = new VerticalLayout(lista1);
			VerticalLayout listas2 = new VerticalLayout(lista2);
			cajas.setWidth("500px");
			listas1.setWidth("400px");
			listas2.setWidth("400px");
			HorizontalLayout formulario = new HorizontalLayout(cajas, listas1, listas2);
			  
			  //formulario.setExpandRatio(cajas, 0.7f);
			  //formulario.setExpandRatio(listas, 0.3f);
			  
			  HorizontalLayout actions = new HorizontalLayout(filter3, filter, filter2/*, addNewBtn*/);
			  HorizontalLayout gridEditor = new HorizontalLayout(grid, editor);
			  
			  VerticalLayout mainLayout = new VerticalLayout(formulario, actions, gridEditor);
			  
			  addComponent(mainLayout);
			  
			        nombre.setPlaceholder("Nombre");
			        
			        descripcion.setValue("Descripción");
			        descripcion.setRows(10);
			        descripcion.setSizeFull();
			        
			        precio.setPlaceholder("Precio");
			        
			        iva.setPlaceholder("Nada seleccionado");
			        
			        stock.setPlaceholder("Stock");
			        
			        lista1.setRows(6);
			        lista1.select(data1.get(2));
			        lista1.setWidth(100.0f, Unit.PERCENTAGE);
			        
			        lista2.setRows(6);
			        lista2.select(data2.get(2));
			        lista2.setWidth(100.0f, Unit.PERCENTAGE);

			  grid.setHeight(500, Unit.PIXELS);
			  grid.setWidth(850, Unit.PIXELS);
			  grid.setColumns("id", "dni", "nombre", "apellidos", "direccion", "telefono");

			  filter.setPlaceholder("Filtrar por nombre");
			  filter2.setPlaceholder("Filtrar por apellidos");
			  filter3.setPlaceholder("Filtrar por dni");

			  // Hook logic to components

			  // Replace listing with filtered content when user changes filter
			  filter.setValueChangeMode(ValueChangeMode.LAZY);
			  filter.addValueChangeListener(e -> ControladorPrincipal.listarEmpleados(e.getValue(), "nombre", grid, repo));
			  filter2.setValueChangeMode(ValueChangeMode.LAZY);
			  filter2.addValueChangeListener(e -> ControladorPrincipal.listarEmpleados(e.getValue(), "apellidos", grid, repo));
			  filter3.setValueChangeMode(ValueChangeMode.LAZY);
			  filter3.addValueChangeListener(e -> ControladorPrincipal.listarEmpleados(e.getValue(), "dni", grid, repo));

			  /*
			  
			  // Connect selected Customer to editor or hide if none is selected
			  grid.asSingleSelect().addValueChangeListener(e -> {
			   editor.editarEmpleado(e.getValue());
			  });

			  
			  // Instantiate and edit new Customer the new button is cl

			Miguelo, [04.05.17 18:14]
			icked
			  addNewBtn.addClickListener(e -> editor.editarEmpleado(new Empleado("", "", "", "", "", "")));
			  // Listen changes made by the editor, refresh data from backend
			  editor.setChangeHandler(() -> {
			   editor.setVisible(false);
			   listarEmpleados(filter.getValue(), "");
			  });
			  */

			  // Initialize listing
			  ControladorPrincipal.listarEmpleados(null, null, grid, repo);
			 }

			  
			  @Override
			  public void enter(ViewChangeEvent event) {
			   // the view is constructed in the init() method()
			  }
}