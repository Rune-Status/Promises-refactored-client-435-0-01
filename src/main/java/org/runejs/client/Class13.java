package org.runejs.client;

import org.runejs.client.node.Class40_Sub6;
import org.runejs.client.cache.media.IndexedImage;
import org.runejs.client.cache.media.gameInterface.GameInterface;
import org.runejs.client.cache.media.gameInterface.GameInterfaceArea;
import org.runejs.client.media.renderable.GameObject;
import org.runejs.client.media.renderable.actor.Actor;
import org.runejs.client.media.renderable.actor.Player;
import org.runejs.client.media.renderable.actor.PlayerAppearance;

public class Class13 {
    /**
     * The image used for the highlighted (selected) tab button,
     * for one of the tabs on the right-hand side of the bottom,
     * but not the furthest-right (see `tabHighlightImageBottomRightEdge` for that).
     */
    public static IndexedImage tabHighlightImageBottomRight;

    public static void handleActorAnimation(Actor actor) {
        if(actor.worldX < 128 || actor.worldY < 128 || actor.worldX >= 13184 || actor.worldY >= 13184) {
            actor.playingAnimation = -1;
            actor.forceMoveEndCycle = 0;
            actor.forceMoveStartCycle = 0;
            actor.graphicId = -1;
            actor.worldX = actor.size * 64 + 128 * actor.pathY[0];
            actor.worldY = actor.pathX[0] * 128 + 64 * actor.size;
            actor.method790(0);
        }
        if(actor == Player.localPlayer && (actor.worldX < 1536 || actor.worldY < 1536 || actor.worldX >= 11776 || actor.worldY >= 11776)) {
            actor.graphicId = -1;
            actor.forceMoveStartCycle = 0;
            actor.forceMoveEndCycle = 0;
            actor.playingAnimation = -1;
            actor.worldX = actor.pathY[0] * 128 + actor.size * 64;
            actor.worldY = 64 * actor.size + actor.pathX[0] * 128;
            actor.method790(0);
        }
        if(actor.forceMoveEndCycle > MovedStatics.pulseCycle)
            updateForcedMovement(actor);
        else if(actor.forceMoveStartCycle < MovedStatics.pulseCycle)
            Class44.processWalkingStep(255, actor);
        else
            PlayerAppearance.startForcedMovement(actor);
        Projectile.updateFacingDirection(actor);
        Class40_Sub5_Sub15.updateAnimation(actor);
    }

    public static void handleRequests() {
        for(; ; ) {
            Class40_Sub6 class40_sub6;
            synchronized(RSCanvas.aLinkedList_53) {
                class40_sub6 = (Class40_Sub6) MovedStatics.aLinkedList_2604.removeFirst();
            }
            if(class40_sub6 == null)
                break;
            class40_sub6.cacheArchive.method198(false, class40_sub6.aByteArray2102, (int) class40_sub6.key, class40_sub6.cacheIndex);
        }
    }

    public static void handleInterfaceActions(GameInterfaceArea area, int mouseX, int mouseY, int minX, int minY, int maxX, int maxY, int widgetId) {
        if(GameInterface.decodeGameInterface(widgetId)) {
            MovedStatics.handleInterfaceActions(area, mouseX, mouseY, minX, minY, maxX, maxY, GameInterface.cachedInterfaces[widgetId], -1, 0, 0);
        }
    }

    public static int[] method247(GameInterface arg0) {
        int i;
        if(arg0.id < 0)
            i = arg0.parentId >> 16;
        else
            i = arg0.id >> 16;
        if(!GameInterface.decodeGameInterface(i))
            return null;
        int i_11_ = arg0.currentX;
        int i_12_ = arg0.currentY;
        int i_13_ = arg0.parentId;
        while(i_13_ != -1) {
            GameInterface gameInterface = GameInterface.cachedInterfaces[i][i_13_ & 0xffff];
            i_11_ += gameInterface.currentX;
            if(!arg0.lockScroll)
                i_11_ -= gameInterface.scrollWidth;
            i_12_ += gameInterface.currentY;
            i_13_ = gameInterface.parentId;
            if(!arg0.lockScroll)
                i_12_ -= gameInterface.scrollPosition;
        }
        int[] is = new int[2];
        is[0] = i_11_;
        is[1] = i_12_;
        return is;
    }

    public static void method249() {
        if(GameObject.frame != null) {
            synchronized(GameObject.frame) {
                GameObject.frame = null;
            }
        }
    }

    public static void updateForcedMovement(Actor actor) {
        int deltaTime = actor.forceMoveEndCycle - MovedStatics.pulseCycle;
        int destX = actor.forceMoveStartX * 128 + 64 * actor.size;
        if(actor.forceMoveFaceDirection == 0)
            actor.initialFaceDirection = 1024;
        if(actor.forceMoveFaceDirection == 1)
            actor.initialFaceDirection = 1536;
        if(actor.forceMoveFaceDirection == 2)
            actor.initialFaceDirection = 0;
        int destY = actor.size * 64 + 128 * actor.forceMoveStartY;
        actor.worldX += (destX - actor.worldX) / deltaTime;
        if(actor.forceMoveFaceDirection == 3)
            actor.initialFaceDirection = 512;
        actor.anInt3074 = 0;
        actor.worldY += (-actor.worldY + destY) / deltaTime;
    }
}
