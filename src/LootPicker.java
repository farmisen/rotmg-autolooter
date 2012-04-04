import org.sikuli.script.*;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class LootPicker /* extends JFrame implements SikuliEventObserver,*/ implements Runnable {
    private List<Slot> inventorySlots = new ArrayList<Slot>();
    private Region gameWindow;
    private List<Slot> lootSlots = new ArrayList<Slot>();
    private Map<Region, Slot> lootSlotsMap = new HashMap<Region, Slot>();
    private Region lootBagCornerRegion;
    private Pattern lootBagCornerPattern;
    private BlockingQueue<Slot> foundLoot = new ArrayBlockingQueue<Slot>(8);
    private boolean isDragging = false;

    public LootPicker(Region gameWindow) {
        this.gameWindow = gameWindow;

        int x = gameWindow.getX() + 614;
        int y = gameWindow.getY() + 430;


        for (int j = 0; j < 2; j++) {
            for (int i = 0; i < 4; i++) {
                Slot slot = new Slot(new Region(x + i * 44, y + j * 44, 42, 42), String.format("images/opaque/slots/slot0%d.png", i + j * 4 + 1), String.format("inventory slot #0%d", i + j * 4 + 1));
                //slot.region.highlight(1);
                inventorySlots.add(slot);
            }
        }

        y = gameWindow.getY() + 530;

        lootBagCornerRegion = new Region(x - 8, y - 8, 16, 16);
        lootBagCornerPattern = new Pattern("images/opaque/slots/lootBagCorner.png");

        for (int j = 0; j < 2; j++) {
            for (int i = 0; i < 4; i++) {
                Slot slot = new Slot(new Region(x + i * 44, y + j * 44, 42, 42), "images/opaque/slots/emptyLootSlot.png", String.format("loot slot #0%d", i + j * 4 + 1));
                //slot.region.highlight(1);
                //slot.region.onChange(this);
                //slot.region.observeInBackground(Float.POSITIVE_INFINITY);
                //lootSlotsMap.put(slot.region, slot);
                lootSlots.add(slot);
            }
        }

        (new Thread(this)).start();

    }

    private boolean isALootBag() {
        try {
            Match res = lootBagCornerRegion.findNow(lootBagCornerPattern);
            //System.out.println("is a loot bag:" + res.getScore() );
            return res != null && res.getScore() > .8f;
        } catch (FindFailed findFailed) {
            System.out.println("is a loot bag:failed");

            return false;
        }
    }

    private Region findEmptySlot() {
        for (Slot slot : inventorySlots) {
            if (slot.empty()) {
                return slot.region;
            }
        }
        return null;
    }

    @Override
    public void run() {

        Location oldLocation = null;
        while (true) {
            try {
                Slot lootSlot = foundLoot.take();
                if ( null == oldLocation ) {
                    oldLocation = Env.getMouseLocation();
                }
                System.out.println("Processing " + lootSlot.name);

                Region slotRegion = findEmptySlot();
                Region lootRegion = lootSlot.region;

                if (null == slotRegion) {
                    System.out.println("no free inventory slot");
                    continue;
                }

                try {
                    //lootRegion.highlight(1);
                    //slotRegion.highlight(1);
                    isDragging = true;
                    lootRegion.dragDrop(lootRegion, slotRegion, 0);
                    isDragging = false;
                } catch (FindFailed findFailed) {
                    isDragging = false;
                    System.out.println("findFailed:" + findFailed.getMessage());
                    findFailed.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }

                if ( foundLoot.isEmpty() ) {
                    try {
                        lootRegion.mouseMove(oldLocation);
                    } catch (FindFailed findFailed) {
                        findFailed.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                    }
                    oldLocation = null;

                }

            } catch (InterruptedException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }
    }

    public void pick() {
        if (!isALootBag()) {
            return;
        }
        for (Slot slot : lootSlots) {
            if (!slot.empty()) {
                System.out.println("Queuing " + slot.name);
                foundLoot.add(slot);
            }
        }
    }
}