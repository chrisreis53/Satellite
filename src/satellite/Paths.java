package satellite;

import gov.nasa.worldwind.View;
import gov.nasa.worldwind.WorldWind;
import gov.nasa.worldwind.avlist.AVKey;
import gov.nasa.worldwind.geom.LatLon;
import gov.nasa.worldwind.geom.Position;
import gov.nasa.worldwind.layers.RenderableLayer;
import gov.nasa.worldwind.render.*;
import gov.nasa.worldwind.util.WWUtil;
import gov.nasa.worldwindx.examples.ApplicationTemplate;

import java.awt.Color;
import java.util.ArrayList;

/**
 * Example of {@link Path} usage. A Path is a line or curve between positions. The path may follow terrain, and may be
 * turned into a curtain by extruding the path to the ground.
 *
 * @author tag
 * @version $Id: Paths.java 1171 2013-02-11 21:45:02Z dcollins $
 */
public class Paths extends ApplicationTemplate
{
	static RenderableLayer layer = new RenderableLayer();
	static ArrayList<satPosition> aSatellite = new ArrayList<satPosition>();
	
	public static void setOrbit(ArrayList<satPosition> sat){
		aSatellite = sat;
        // Create and set an attribute bundle.
        ShapeAttributes attrs = new BasicShapeAttributes();
        attrs.setOutlineMaterial(new Material(WWUtil.makeRandomColor(null)));
        attrs.setOutlineWidth(2d);
        
        ArrayList<Position> pathPositions = new ArrayList<Position>();
        
        for(int i=0;i<aSatellite.size(); i++){
        	pathPositions.add(Position.fromDegrees(aSatellite.get(i).getLat(), aSatellite.get(i).getLon(), aSatellite.get(i).getAlt()*1000));
        }
        Path path = new Path(pathPositions);
        path.setAttributes(attrs);
        path.setVisible(true);
        path.setAltitudeMode(WorldWind.RELATIVE_TO_GROUND);
        path.setPathType(AVKey.GREAT_CIRCLE);
        layer.addRenderable(path);

        attrs = new BasicShapeAttributes();
        attrs.setOutlineMaterial(new Material(WWUtil.makeRandomColor(null)));
        attrs.setInteriorMaterial(new Material(WWUtil.makeRandomColor(null)));
        attrs.setOutlineWidth(2);
        path.setAttributes(attrs);


        
        //layer.addRenderable(path);
        //this.getLayerPanel().update(this.getWwd());
	}
	
	public static void setPosition(ArrayList<satPosition> aSatellite, String name){
        PointPlacemark pp = new PointPlacemark(Position.fromDegrees(aSatellite.get(0).getLat(),aSatellite.get(0).getLon(),aSatellite.get(0).getAlt()*1000));;
        pp.setLabelText(name);
        pp.setValue(AVKey.DISPLAY_NAME, "Label, Semi-transparent, Audio icon");
        pp.setLineEnabled(false);
        pp.setAltitudeMode(WorldWind.RELATIVE_TO_GROUND);
        PointPlacemarkAttributes attrsP = new PointPlacemarkAttributes();
        attrsP.setImageAddress("satellite.png");
        //attrsP.setImageColor(new Color(1f, 1f, 1f, 0.6f));
        attrsP.setScale(0.1);
        attrsP.setImageOffset(new Offset(175d,175d,AVKey.PIXELS, AVKey.PIXELS));
        pp.setAttributes(attrsP);
        layer.addRenderable(pp);
	}
	
    public static class AppFrame extends ApplicationTemplate.AppFrame
    {
        public AppFrame()
        {
            super(true, true, false);

            // Add the layer to the model.
            insertBeforeCompass(getWwd(), layer);

            // Update layer panel
            this.getLayerPanel().update(this.getWwd());
            
//            //goto coord
//            LatLon latlon = LatLon.fromDegrees(aSatellite.get(1).getLat(),aSatellite.get(1).getLon());
//            System.out.println(latlon + " " + aSatellite.get(1).getAlt());
//            View view = this.getWwd().getView();
//            //double distance = view.getCenterPoint().distanceTo3(view.getEyePoint());
//            view.goTo(new Position(latlon, 0.0), 0.0);
        }
    }

//    public static void main(String[] args)
//    {
//        ApplicationTemplate.start("World Wind Paths", AppFrame.class);
//    }
}
