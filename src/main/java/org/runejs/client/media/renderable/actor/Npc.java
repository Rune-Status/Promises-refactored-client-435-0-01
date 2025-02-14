package org.runejs.client.media.renderable.actor;

import org.runejs.client.*;
import org.runejs.client.cache.def.ActorDefinition;
import org.runejs.client.cache.media.AnimationSequence;
import org.runejs.client.cache.def.SpotAnimDefinition;
import org.runejs.client.media.renderable.Model;
import org.runejs.client.net.PacketBuffer;

public class Npc extends Actor {
    public ActorDefinition actorDefinition;

    public static void parseNpcUpdateMasks(PacketBuffer buffer) {
        for (int i = 0; i < actorUpdatingIndex; i++) {
            int npcIndex = -1;
            Npc npc = null;

            boolean alreadyGotWorldIndex = false;

            int mask = buffer.getUnsignedByte();
            if ((0x1 & mask) != 0) {
                int i_3_ = buffer.getUnsignedByte();
                int i_4_ = buffer.getUnsignedByte();
                int remainingHitpoints = buffer.getUnsignedByte();
                int maximumHitpoints = buffer.getUnsignedByte();

                // the NPC's worldIndex bytes won't be included if already
                // sent earlier
                if (!alreadyGotWorldIndex) {
                    npcIndex = buffer.getUnsignedShortBE();
                    npc = Player.npcs[npcIndex];
                    alreadyGotWorldIndex = true;
                }

                npc.method785(i_4_, MovedStatics.pulseCycle, i_3_);
                npc.anInt3139 = MovedStatics.pulseCycle + 300;
                npc.remainingHitpoints = remainingHitpoints;
                npc.maximumHitpoints = maximumHitpoints;
            }
            if ((0x20 & mask) != 0) {
                int graphicId = buffer.getUnsignedShortLE();
                int i_5_ = buffer.getIntBE();

                // the NPC's worldIndex bytes won't be included if already
                // sent earlier
                if (!alreadyGotWorldIndex) {
                    npcIndex = buffer.getUnsignedShortBE();
                    npc = Player.npcs[npcIndex];
                    alreadyGotWorldIndex = true;
                }
                npc.graphicId = graphicId;
                npc.anInt3129 = 0;
                npc.graphicDelay = MovedStatics.pulseCycle + (0xffff & i_5_);
                npc.graphicHeight = i_5_ >> 16;
                npc.anInt3140 = 0;
                if (npc.graphicDelay > MovedStatics.pulseCycle)
                    npc.anInt3140 = -1;
                if (npc.graphicId == 65535)
                    npc.graphicId = -1;
            }
            if ((mask & 0x4) != 0) {
                int facingActorIndex = buffer.getUnsignedShortBE();

                // the NPC's worldIndex bytes won't be included if already
                // sent earlier
                if (!alreadyGotWorldIndex) {
                    npcIndex = buffer.getUnsignedShortBE();
                    npc = Player.npcs[npcIndex];
                    alreadyGotWorldIndex = true;
                }

                npc.facingActorIndex = facingActorIndex;

                if (npc.facingActorIndex == 65535)
                    npc.facingActorIndex = -1;
            }
            if ((0x2 & mask) != 0) {
                int i_6_ = buffer.getUnsignedByte();
                int i_7_ = buffer.getUnsignedByte();

                int remainingHitpoints = buffer.getUnsignedByte();
                int maximumHitpoints = buffer.getUnsignedByte();

                // the NPC's worldIndex bytes won't be included if already
                // sent earlier
                if (!alreadyGotWorldIndex) {
                    npcIndex = buffer.getUnsignedShortBE();
                    npc = Player.npcs[npcIndex];
                    alreadyGotWorldIndex = true;
                }

                npc.method785(i_7_, MovedStatics.pulseCycle, i_6_);
                npc.anInt3139 = MovedStatics.pulseCycle + 300;

                npc.remainingHitpoints = remainingHitpoints;
                npc.maximumHitpoints = maximumHitpoints;
            }
            if ((0x40 & mask) != 0) {
                String forcedChatMessage = buffer.getString();

                // the NPC's worldIndex bytes won't be included if already
                // sent earlier
                if (!alreadyGotWorldIndex) {
                    npcIndex = buffer.getUnsignedShortBE();
                    npc = Player.npcs[npcIndex];
                    alreadyGotWorldIndex = true;
                }

                npc.forcedChatMessage = forcedChatMessage;

                npc.chatTimer = 100;
            }
            if ((mask & 0x80) != 0) {
                int actorDefinition = buffer.getUnsignedShortBE();

                // the NPC's worldIndex bytes won't be included if already
                // sent earlier
                if (!alreadyGotWorldIndex) {
                    npcIndex = buffer.getUnsignedShortBE();
                    npc = Player.npcs[npcIndex];
                    alreadyGotWorldIndex = true;
                }

                npc.actorDefinition = ActorDefinition.getDefinition(actorDefinition);
                npc.anInt3083 = npc.actorDefinition.rotateRightAnimation;
                npc.anInt3113 = npc.actorDefinition.degreesToTurn;
                npc.turnRightAnimationId = npc.actorDefinition.rotate90RightAnimation;
                npc.idleAnimation = npc.actorDefinition.stanceAnimation;
                npc.walkAnimationId = npc.actorDefinition.walkAnimation;
                npc.standTurnAnimationId = npc.actorDefinition.rotateLeftAnimation;
                npc.size = npc.actorDefinition.boundaryDimension;
                npc.turnLeftAnimationId = npc.actorDefinition.rotate90LeftAnimation;
                npc.turnAroundAnimationId = npc.actorDefinition.rotate180Animation;
            }
            if ((mask & 0x8) != 0) {
                int facePositionX = buffer.getUnsignedShortBE();
                int facePositionY = buffer.getUnsignedShortLE();

                // the NPC's worldIndex bytes won't be included if already
                // sent earlier
                if (!alreadyGotWorldIndex) {
                    npcIndex = buffer.getUnsignedShortBE();
                    npc = Player.npcs[npcIndex];
                    alreadyGotWorldIndex = true;
                }

                npc.facePositionX = facePositionX;
                npc.facePositionY = facePositionY;
            }
            if ((0x10 & mask) != 0) {
                int animationId = buffer.getUnsignedShortBE();
                int animationDelay = buffer.getUnsignedByte();

                // the NPC's worldIndex bytes won't be included if already
                // sent earlier
                if (!alreadyGotWorldIndex) {
                    npcIndex = buffer.getUnsignedShortBE();
                    npc = Player.npcs[npcIndex];
                    alreadyGotWorldIndex = true;
                }

                if (animationId == 65535) {
                    animationId = -1;
                }

                if (animationId == npc.playingAnimation && animationId != -1) {
                    int i_10_ = AnimationSequence.getAnimationSequence(animationId).replyMode;
                    if (i_10_ == 1) {
                        npc.anInt3115 = 0;
                        npc.anInt3095 = 0;
                        npc.anInt3104 = 0;
                        npc.playingAnimationDelay = animationDelay;
                    }
                    if (i_10_ == 2)
                        npc.anInt3095 = 0;
                } else if (animationId == -1 || npc.playingAnimation == -1
                        || AnimationSequence.getAnimationSequence(animationId).forcedPriority >= AnimationSequence
                                .getAnimationSequence(npc.playingAnimation).forcedPriority) {
                    npc.playingAnimation = animationId;
                    npc.anInt3115 = 0;
                    npc.playingAnimationDelay = animationDelay;
                    npc.anInt3104 = 0;
                    npc.anInt3095 = 0;
                    npc.anInt3094 = npc.anInt3109;
                }
            }
        }
    }

    public Model getRotatedModel() {
        if(actorDefinition == null)
            return null;
        AnimationSequence animationSequence = playingAnimation == -1 || playingAnimationDelay != 0 ? null : AnimationSequence.getAnimationSequence(playingAnimation);
        AnimationSequence animationSequence_0_ = anInt3077 != -1 && (anInt3077 != idleAnimation || animationSequence == null) ? AnimationSequence.getAnimationSequence(anInt3077) : null;
        Model model = actorDefinition.getChildModel(animationSequence, animationSequence_0_, anInt3116, anInt3104);
        if(model == null)
            return null;
        model.method799();
        anInt3117 = model.modelHeight;
        if(graphicId != -1 && anInt3140 != -1) {
            Model model1 = SpotAnimDefinition.forId(graphicId).getModel(anInt3140);
            if(model1 != null) {
                model1.translate(0, -graphicHeight, 0);
                Model[] models = {model, model1};
                model = new Model(models, 2, true);
            }
        }
        if(actorDefinition.boundaryDimension == 1)
            model.singleTile = true;
        return model;
    }

    public boolean isInitialized() {
        return actorDefinition != null;
    }
}
