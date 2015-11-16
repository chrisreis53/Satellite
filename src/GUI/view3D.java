package GUI;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JInternalFrame;

import gov.nasa.worldwind.BasicModel;
import gov.nasa.worldwind.WorldWind;
import gov.nasa.worldwind.avlist.AVKey;
import gov.nasa.worldwind.awt.WorldWindowGLCanvas;
import gov.nasa.worldwind.geom.Position;
import gov.nasa.worldwind.layers.Layer;
import gov.nasa.worldwind.layers.LayerList;
import gov.nasa.worldwind.layers.RenderableLayer;
import gov.nasa.worldwind.render.Offset;
import gov.nasa.worldwind.render.PointPlacemark;
import gov.nasa.worldwind.render.PointPlacemarkAttributes;
import gov.nasa.worldwind.render.Renderable;
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
    
    public void addTrack(String name, Double lat, Double lon){
    	
        
        Iterable<Renderable> iter = layer.getRenderables();
        while(iter.iterator().hasNext()){
        	Renderable rend = iter.iterator().next();
        	if(rend instanceof PointPlacemark){
        		PointPlacemark delp = (PointPlacemark)rend;
        		if(delp.getLabelText().equals(name)){
        			delp.setPosition(Position.fromDegrees(lat, lon));
        			wwd.redraw();
        			return;
        		}
        	}
        }
        PointPlacemark pp = new PointPlacemark(Position.fromDegrees(lat, lon, 1e4));
        pp.setValue(AVKey.DISPLAY_NAME, "Clamp to ground, Audio icon, Heading -45, Globe relative");
        pp.setLabelText(name);
        pp.setLineEnabled(false);
        pp.setAltitudeMode(WorldWind.CLAMP_TO_GROUND);
        PointPlacemarkAttributes attrs = new PointPlacemarkAttributes();
        attrs.setImageAddress("D:\\MyDocuments\\GitHub\\Satellite\\satellite.png");
        attrs.setHeading(-45d);
        attrs.setHeadingReference(AVKey.RELATIVE_TO_GLOBE);
        attrs.setScale(0.05);
//        attrs.setImageOffset(new Offset(0.5, 0.5, AVKey.FRACTION, AVKey.FRACTION));
        attrs.setImageOffset(new Offset(19d, 8d, AVKey.PIXELS, AVKey.PIXELS));
        attrs.setLabelColor("ffffffff");
        attrs.setLabelOffset(new Offset(0.9d, 0.6d, AVKey.FRACTION, AVKey.FRACTION));
        pp.setAttributes(attrs);
        layer.addRenderable(pp);
        
        // Add the layer to the model.
        wwd.getModel().getLayers().add(layer);
        // Update layer panel
        wwd.redraw();
    }

    public void addSatellite(List<satPosition> positions){
    	for(int i = 0; i<positions.size();i++){
    		addTrack(positions.get(i).getName(),positions.get(i).getLat(),positions.get(i).getLon());
    	}
    }
}