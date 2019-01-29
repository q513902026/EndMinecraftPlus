package luohuayu.EndMinecraftPlus.tasks.attack;

import org.spacehq.mc.protocol.packet.ingame.client.ClientSettingsPacket;
import org.spacehq.mc.protocol.packet.ingame.client.ClientTabCompletePacket;
import org.spacehq.mc.protocol.packet.ingame.client.player.ClientPlayerPositionRotationPacket;
import org.spacehq.packetlib.Session;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;

public class MultiVersionPacket {
    public static void sendTabPacket(Session session, String text) {
        try {
            Class<?> cls = ClientTabCompletePacket.class;
            Constructor<?> constructor = cls.getDeclaredConstructor();
            constructor.setAccessible(true);
            ClientTabCompletePacket packet = (ClientTabCompletePacket) constructor.newInstance();
            Field field = cls.getDeclaredField("text");
            field.setAccessible(true);
            field.set(packet, text);
            session.send(packet);
        } catch (Exception e) {}
    }

    public static void sendPosPacket(Session session, double x, double y, double z, float yaw, float pitch) {
        try {
            Class<?> cls = ClientPlayerPositionRotationPacket.class;
            Constructor<?> constructor;
            ClientPlayerPositionRotationPacket packet;
            try {
                constructor = cls.getConstructor(boolean.class, double.class, double.class, double.class, float.class, float.class);
                packet = (ClientPlayerPositionRotationPacket) constructor.newInstance(true, x, y, z, yaw, pitch);
            } catch (NoSuchMethodException ex) {
                constructor = cls.getConstructor(boolean.class, double.class, double.class, double.class, double.class, float.class, float.class);
                packet = (ClientPlayerPositionRotationPacket) constructor.newInstance(true, x, y - 1.62, y , z, yaw, pitch);
            }
            session.send(packet);
        } catch (Exception e) {}
    }

    public static void sendClinetSettingPacket(Session session, String locale) {
        try {
            Class<?> cls = ClientSettingsPacket.class;
            Constructor<?> constructor;
            ClientSettingsPacket packet;
            try {
                Class<?> parm1Class = Class.forName("org.spacehq.mc.protocol.data.game.setting.ChatVisibility");
                Class<?> parm2Class = Class.forName("[org.spacehq.mc.protocol.data.game.setting.SkinPart");
                Class<?> parm3Class = Class.forName("org.spacehq.mc.protocol.data.game.entity.player.Hand");

                constructor = cls.getConstructor(String.class, int.class, parm1Class, boolean.class, parm2Class, parm3Class);
                packet = (ClientSettingsPacket) constructor.newInstance(locale, 10, parm1Class.getEnumConstants(), true, new Object[] {parm2Class.getEnumConstants()[0]}, parm3Class.getEnumConstants()[0]);
            } catch (NoSuchMethodException ex) {
                Class<?> parm1Class = Class.forName("org.spacehq.mc.protocol.packet.ingame.client.ClientSettingsPacket.ChatVisibility");
                Class<?> parm2Class = Class.forName("org.spacehq.mc.protocol.packet.ingame.client.ClientSettingsPacket.Difficulty");

                constructor = cls.getConstructor(String.class, int.class, parm1Class, boolean.class, parm2Class, boolean.class);
                packet = (ClientSettingsPacket) constructor.newInstance(locale, 10, parm1Class.getEnumConstants()[0], true, parm2Class.getEnumConstants()[0], true);
            }
            session.send(packet);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
