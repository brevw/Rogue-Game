package ch.epfl.cs107.play.game.icrogue;
import ch.epfl.cs107.play.game.areagame.AreaBehavior;
import ch.epfl.cs107.play.game.areagame.actor.Interactable;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.game.icrogue.handler.ICRogueInteractionHandler;
import ch.epfl.cs107.play.window.Window;


public class ICRogueBehavior extends AreaBehavior {
    public enum ICRogueCellType{

        NONE (0, false ), // Should never be used except in the toType method
        GROUND ( -16777216 , true), // traversable
        WALL ( -14112955 , false ), // non traversable
        HOLE ( -65536 , true);

        final int type;
        final boolean isWalkable;

        ICRogueCellType(int type, boolean isWalkable){
            this.type = type;
            this.isWalkable = isWalkable;
        }

        public static ICRogueCellType toType(int type){
            for(ICRogueCellType ict : ICRogueCellType.values()){
                if(ict.type == type)
                    return ict;
            }
            // When you add a new color, you can print the int value here before assign it to a type
            System.out.println(type);
            return NONE;
        }
    }

    /**
     * Cell adapted to the ICRogue game
     */
    public class ICRogueCell extends Cell{
        private boolean isCellTaken;
        /// Type of the cell following the enum
        private final ICRogueCellType type;

        /**
         * Default ICRogueCell Constructor
         * @param x (int): x coordinate of the cell
         * @param y (int): y coordinate of the cell
         * @param type (ICRogue), not null
         */
        public  ICRogueCell(int x, int y, ICRogueCellType type){
            super(x, y);
            this.type = type;
        }

        public ICRogueCellType getType() {
            return type;
        }
        @Override
        protected boolean canLeave(Interactable entity) {
            return true;
        }

        @Override
        protected boolean canEnter(Interactable entity) {
            if(type.isWalkable) {
                ICRogueCell cell = (ICRogueCell) getCell(getCurrentCells().get(0).x,getCurrentCells().get(0).y);
                for(Interactable interactable:cell.entities){
                    if(interactable.takeCellSpace()){
                        return false;
                    }
                }
                return true;
            }
            return false;


        }


        @Override
        public boolean isCellInteractable() {
            return true;
        }

        @Override
        public boolean isViewInteractable() {
            return false;
        }

        @Override
        public void acceptInteraction(AreaInteractionVisitor v, boolean isCellInteraction) {
            ((ICRogueInteractionHandler) v).interactWith(this, isCellInteraction);
        }
    }

    /**
     * Default ICRogueBehavior Constructor
     * @param window (Window). Not null
     * @param behaviorName (String): Name of the Behavior. Not null
     */
    public ICRogueBehavior(Window window, String behaviorName){
        super(window, behaviorName);
        int height = getHeight();
        int width = getWidth();
        for(int y = 0; y < height; y++) {
            for (int x = 0; x < width ; x++) {
                ICRogueCellType color = ICRogueCellType.toType(getRGB(height-1-y, x));
                setCell(x,y, new ICRogueCell(x,y,color));
            }
        }
    }
}