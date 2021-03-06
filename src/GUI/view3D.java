package GUI;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JInternalFrame;

import gov.nasa.worldwind.BasicModel;
import gov.nasa.worldwind.WorldWind;
import gov.nasa.worldwind.avlist.AVKey;
import gov.nasa.worldwind.awt.WorldWindowGLCanvas;
import gov.nasa.worldwind.geom.LatLon;
import gov.nasa.worldwind.geom.Position;
import gov.nasa.worldwind.layers.Layer;
import gov.nasa.worldwind.layers.LayerList;
import gov.nasa.worldwind.layers.RenderableLayer;
import gov.nasa.worldwind.render.BasicShapeAttributes;
import gov.nasa.worldwind.render.Cone;
import gov.nasa.worldwind.render.Material;
import gov.nasa.worldwind.render.Offset;
import gov.nasa.worldwind.render.PointPlacemark;
import gov.nasa.worldwind.render.PointPlacemarkAttributes;
import gov.nasa.worldwind.render.Renderable;
import gov.nasa.worldwind.render.ShapeAttributes;
import satellite.satPosition;

public class view3D extends JInternalFrame{
	
	private List<satPosition> trackList = new ArrayList<satPosition>();
	WorldWindowGLCanvas wwd;
	RenderableLayer layer = new RenderableLayer();
	
    public view3D()
    {
        wwd = new WorldWindowGLCanvas();
        wwd.setPreferredSize(new java.awt.Dimension(500, 500));
        this.getContentPane().add(wwd, java.awt.BorderLayout.CENTER);
        wwd.setModel(new BasicModel());
        //Add Layers
        
    }
    
    public void updateTracks(List<satPosition> positions){
    	LayerList layers = wwd.getModel().getLayers();
    	//wwd.getModel().getLayers().getLayerByName("Satellite");
    	for(int i = 0;i<layers.size();i++){
    		if(layers.get(i).getName().equals("Satellite")){
    			layers.get(i).dispose();
    			for(int j = 0;j<positions.size();j++){
    				PointPlacemark pp = new PointPlacemark(Position.fromDegrees(positions.get(j).getLat(),positions.get(j).getLon() , positions.get(j).getAlt()));
    	            pp.setLabelText(positions.get(j).getName());
    	            pp.setValue(AVKey.DISPLAY_NAME, "Clamp to ground, Label, Semi-transparent, Audio icon");
    	            pp.setLineEnabled(false);
    	            pp.setAltitudeMode(WorldWind.CLAMP_TO_GROUND);
    	            PointPlacemarkAttributes attrs = new PointPlacemarkAttributes();
    	            attrs.setImageAddress("gov/nasa/worldwindx/examples/images/audioicon-64.png");
    	            attrs.setImageColor(new Color(1f, 1f, 1f, 0.6f));
    	            attrs.setScale(0.6);
//    	            attrs.setImageOffset(new Offset(19d, 8d, AVKey.PIXELS, AVKey.PIXELS));
    	            attrs.setLabelOffset(new Offset(0.9d, 0.6d, AVKey.FRACTION, AVKey.FRACTION));
    	            pp.setAttributes(attrs);
    	            ((RenderableLayer) layers.get(i)).addRenderable(pp);
    			}
    			wwd.getModel().setLayers(layers);
    			wwd.redraw();
    			return;
    		}
			for(int j = 0;j<positions.size();j++){
				PointPlacemark pp = new PointPlacemark(Position.fromDegrees(positions.get(j).getLat(),positions.get(j).getLon() , positions.get(j).getAlt()));
	            pp.setLabelText(positions.get(j).getName());
	            pp.setValue(AVKey.DISPLAY_NAME, "Clamp to ground, Label, Semi-transparent, Audio icon");
	            pp.setLineEnabled(false);
	            pp.setAltitudeMode(WorldWind.CLAMP_TO_GROUND);
	            PointPlacemarkAttributes attrs = new PointPlacemarkAttributes();
	            attrs.setImageAddress("gov/nasa/worldwindx/examples/images/audioicon-64.png");
	            attrs.setImageColor(new Color(1f, 1f, 1f, 0.6f));
	            attrs.setScale(0.6);
//	            attrs.setImageOffset(new Offset(19d, 8d, AVKey.PIXELS, AVKey.PIXELS));
	            attrs.setLabelOffset(new Offset(0.9d, 0.6d, AVKey.FRACTION, AVKey.FRACTION));
	            pp.setAttributes(attrs);
	            ((RenderableLayer) layers.get(i)).addRenderable(pp);
			}
			wwd.getModel().setLayers(layers);
			wwd.redraw();
			return;
    	}
    }
    
    public void addTrack(String name, Double lat, Double lon, Double alt){
    	
        List<Renderable> rList = new ArrayList<Renderable>();
        Iterable<Renderable> iter = layer.getRenderables();
        for(int i = 0; i<layer.getNumRenderables();i++){
        	if(iter.iterator().next() instanceof PointPlacemark){
        		PointPlacemark pp = (PointPlacemark) rList.get(i);
        		if(pp.getLabelText().equals(name)){
        			pp.setPosition(Position.fromDegrees(lat, lon, alt *1000));
        			System.out.println(name + " updated");
        			wwd.redraw();
        			return;
        		}
        	}
        }
//        while(iter.iterator().hasNext()){
//        	System.out.println("Iterator ");
//        	Renderable rend = iter.iterator().next();
//        	if(rend instanceof PointPlacemark){
//        		PointPlacemark delp = (PointPlacemark)rend;
//        		System.out.println(delp.getLabelText());
//        		if(delp.getLabelText().equals(name)){
//        			delp.setPosition(Position.fromDegrees(lat, lon, alt* 1000));
//        			System.out.println(lat + " " + lon + " " + alt*1000);
//        			wwd.redraw();
//        			return;
//        		}else{
//        			return;
//        		}
//        	} else {
//        		System.out.println("Not a PP");
//        	}
//        }
        //Satellite Placemark
        PointPlacemark pp = new PointPlacemark(Position.fromDegrees(lat, lon, alt*1000));
        pp.setLabelText(name);
        pp.setValue(AVKey.DISPLAY_NAME, "Label, Semi-transparent, Audio icon");
        pp.setLineEnabled(false);
        pp.setAltitudeMode(WorldWind.RELATIVE_TO_GROUND);
        PointPlacemarkAttributes attrsP = new PointPlacemarkAttributes();
        attrsP.setImageAddress("satellite.png");
        //attrsP.setImageColor(new Color(1f, 1f, 1f, 0.6f));
        attrsP.setScale(0.1);
        //attrsP.setImageOffset(new Offset(175d,175d,AVKey.PIXELS, AVKey.PIXELS));
        pp.setAttributes(attrsP);
        layer.addRenderable(pp);
        
        // Create and set an attribute bundle.
        ShapeAttributes attrs = new BasicShapeAttributes();
        attrs.setInteriorMaterial(Material.YELLOW);
        attrs.setInteriorOpacity(0.03);
        attrs.setEnableLighting(true);
        attrs.setOutlineMaterial(Material.RED);
        attrs.setOutlineWidth(2d);
        attrs.setDrawInterior(true);
        attrs.setDrawOutline(false);
        
        // Cone with a texture, using Cone(position, height, radius) constructor
        Cone cone9 = new Cone(Position.fromDegrees(lat, lon), alt * 3000, 600000);
        cone9.setAltitudeMode(WorldWind.RELATIVE_TO_GROUND);
        cone9.setAttributes(attrs);
        cone9.setVisible(true);
        cone9.setValue(AVKey.DISPLAY_NAME, "Cone with a texture");
        layer.addRenderable(cone9);
      
        // Add the layer to the model.
        wwd.getModel().getLayers().add(layer);
        // Update layer panel
        wwd.redraw();
    }

    public void addSatellite(List<satPosition> positions){
    	System.out.println("Size " + positions.size());
    	//layer.dispose();
    	for(int i = 0; i<positions.size();i++){
    		addTrack(positions.get(i).getName(),positions.get(i).getLat(),positions.get(i).getLon(),positions.get(i).getAlt());
    		System.out.println(positions.get(i).getName()+ " Added!");
    	}
    }
}
