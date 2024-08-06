package com.certification.formdatabinding.views.formdatabind.practice;

import com.certification.formdatabinding.views.CertTrainingView;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.theme.lumo.LumoUtility.Margin;

@PageTitle("Empty")
@Route(value = "", layout = CertTrainingView.class)
@RouteAlias(value = "", layout = CertTrainingView.class)
public class Practice_1 extends VerticalLayout {

  public Practice_1() {

    setSpacing(false);

    Image img = new Image("images/empty-plant.png", "placeholder plant");
    img.setWidth("200px");
    add(img);

    H2 header = new H2("This place intentionally left empty");
    header.addClassNames(Margin.Top.XLARGE, Margin.Bottom.MEDIUM);
    add(header);
    add(new Paragraph("It’s a place where you can grow your own UI 🤗"));

    setSizeFull();
    setJustifyContentMode(JustifyContentMode.CENTER);
    setDefaultHorizontalComponentAlignment(Alignment.CENTER);
    getStyle().set("text-align", "center");
  }

}