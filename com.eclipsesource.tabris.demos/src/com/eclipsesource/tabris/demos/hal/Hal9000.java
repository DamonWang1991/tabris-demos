/*******************************************************************************
 * Copyright (c) 2013 EclipseSource and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    EclipseSource - initial API and implementation
 ******************************************************************************/
package com.eclipsesource.tabris.demos.hal;

import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;

public class Hal9000 {

  private static final int PLATE_BORDER = 4;
  private static final int PLATE_FONT_SIZE = 28;
  private static final int BORDER = 6;
  private static final int TOP_HEIGHT_PERCENTAGE = 65;
  private Color borderColor;
  private Label output;
  private Image halEye;
  private int fontSize;

  public Hal9000( Composite parent ) {
    initSizeAgnosticParts( parent.getDisplay() );
    createContent( parent );
  }

  public Label getText() {
    return output;
  }

  private void createContent( Composite parent ) {
    Display display = parent.getDisplay();
    Composite content = new Composite( parent, SWT.NONE );
    borderColor = display.getSystemColor( SWT.COLOR_GRAY );
    content.setBackground( borderColor );
    content.setLayout( new FormLayout() );
    Composite top = createBlackComposite( content );
    top.setLayout( createGridLayout( 1, true, 40, 10, 0 ) );
    FormData formDataTop = createTopLayoutData();
    top.setLayoutData( formDataTop );
    Composite bottom = createBlackComposite( content );
    bottom.setLayout( createGridLayout( 1, true, 10, 10, 0 ) );
    bottom.setBackgroundImage( loadImage( display, "/rubber_grip.png" ) );
    FormData formDataBottom = createBottomLayoutData( top, formDataTop );
    bottom.setLayoutData( formDataBottom );
    createTypePlate( top );
    createEye( top );
    createGrille( bottom );
  }

  private Composite createBlackComposite( Composite parent ) {
    Composite blackComposite = new Composite( parent, SWT.NONE );
    blackComposite.setBackground( parent.getDisplay().getSystemColor( SWT.COLOR_BLACK ) );
    return blackComposite;
  }

  public GridLayout createGridLayout( int numColumns,
                                      boolean makeColumnsEqual,
                                      int marginWidth,
                                      int marginHeight,
                                      int spacing )
  {
    GridLayout result = new GridLayout( numColumns, makeColumnsEqual );
    result.horizontalSpacing = spacing;
    result.verticalSpacing = spacing;
    result.marginWidth = marginWidth;
    result.marginHeight = marginHeight;
    return result;
  }

  private FormData createTopLayoutData() {
    FormData formDataTop = new FormData();
    formDataTop.top = new FormAttachment( 0, BORDER );
    formDataTop.left = new FormAttachment( 0, BORDER );
    formDataTop.right = new FormAttachment( 100, -BORDER );
    formDataTop.bottom = new FormAttachment( TOP_HEIGHT_PERCENTAGE, 0 );
    return formDataTop;
  }

  private FormData createBottomLayoutData( Composite top, FormData formDataTop ) {
    FormData formDataBottom = new FormData();
    formDataBottom.top = new FormAttachment( top, BORDER );
    formDataBottom.left = formDataTop.left;
    formDataBottom.right = formDataTop.right;
    formDataBottom.bottom = new FormAttachment( 100, -BORDER );
    return formDataBottom;
  }

  private void createTypePlate( Composite parent ) {
    Composite container = new Composite( parent, SWT.NONE );
    container.setBackground( borderColor );
    GridData gridData = new GridData( SWT.CENTER, SWT.FILL, true, false );
    gridData.widthHint = 200;
    container.setLayoutData( gridData );
    GridLayout layout = GridLayoutFactory.fillDefaults().numColumns( 2 ).equalWidth( true )
                        .margins( PLATE_BORDER, PLATE_BORDER ).spacing( 0, 0 ).create();
    container.setLayout( layout );
    Color blue = new Color( parent.getDisplay(), 1, 116, 255 );
    Color black = parent.getDisplay().getSystemColor( SWT.COLOR_BLACK );
    createPlateLabel( container, "HAL", SWT.RIGHT, blue );
    createPlateLabel( container, "9000", SWT.LEFT, black );
  }

  private void createPlateLabel( Composite parent,
                                 String text,
                                 int alignment,
                                 Color background )
  {
    GridData labelLayoutData = new GridData( SWT.FILL, SWT.FILL, true, true );
    Label hal = new Label( parent, alignment );
    hal.setBackground( background );
    hal.setForeground( parent.getDisplay().getSystemColor( SWT.COLOR_WHITE ) );
    hal.setText( text );
    hal.setLayoutData( labelLayoutData );
    hal.setFont( createBold( parent.getDisplay(), PLATE_FONT_SIZE ) );
  }

  private void createEye( Composite parent ) {
    Composite eyeContainer = new Composite( parent, SWT.NONE );
    GridData container2Data = new GridData( SWT.FILL, SWT.FILL, false, true );
    eyeContainer.setBackground( parent.getDisplay().getSystemColor( SWT.COLOR_BLACK ) );
    eyeContainer.setLayoutData( container2Data );
    eyeContainer.setLayout( new FormLayout() );
    final Label eyeLabel = new Label( eyeContainer, SWT.CENTER );
    eyeLabel.setBackground( parent.getDisplay().getSystemColor( SWT.COLOR_BLACK ) );
    eyeLabel.setImage( halEye );
    eyeLabel.setLayoutData( createFormDataEye() );
  }

  private void initSizeAgnosticParts( Display display ) {
    Rectangle bounds = display.getMonitors()[ 0 ].getBounds();
    int width = Math.min( bounds.width, bounds.height );
    if( width < 400 ) {
      halEye = loadImage( display, "/hal_160.png" );
      fontSize = 18;
    } else {
      halEye = loadImage( display, "/hal_240.png" );
      fontSize = 24;
    }
  }

  private FormData createFormDataEye() {
    FormData formDataEye = new FormData();
    formDataEye.left = new FormAttachment( 0, 0 );
    formDataEye.right = new FormAttachment( 100, 0 );
    formDataEye.bottom = new FormAttachment( 90, 0 );
    return formDataEye;
  }

  private Font createBold( Display display, int size ) {
    FontData fontData = new FontData();
    fontData.setStyle( SWT.BOLD );
    fontData.setHeight( size );
    return new Font( display, fontData );
  }

  private void createGrille( Composite parent ) {
    Composite textContainer = new Composite( parent, SWT.NONE );
    GridData grilleData = new GridData( SWT.FILL, SWT.FILL, true, true );
    textContainer.setLayoutData( grilleData );
    GridLayout layout = GridLayoutFactory.fillDefaults().equalWidth( true ).margins( 0, 0 ).spacing( 0, 0 ).create();
    textContainer.setLayout( layout );
    output = new Label( textContainer, SWT.WRAP );
    output.setLayoutData( new GridData( SWT.FILL, SWT.FILL, true, true ) );
    output.setForeground( parent.getDisplay().getSystemColor( SWT.COLOR_GREEN ) );
    output.setFont( createBold( parent.getDisplay(), fontSize ) );
    output.setText( "Good morning Dave.\n\nYou can send me to sleep by changing to another app.\n" );
  }

  private Image loadImage( Display display, String path ) {
    return new Image( display, Hal9000.class.getResourceAsStream( path ) );
  }
}
