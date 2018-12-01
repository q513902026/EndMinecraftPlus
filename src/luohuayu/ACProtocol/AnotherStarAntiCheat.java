package luohuayu.ACProtocol;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import org.spacehq.opennbt.tag.builtin.ByteArrayTag;
import org.spacehq.opennbt.tag.builtin.ByteTag;
import org.spacehq.opennbt.tag.builtin.CompoundTag;
import org.spacehq.opennbt.tag.builtin.ListTag;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.ByteBufOutputStream;

public class AnotherStarAntiCheat {
    public void ctsEncode(ByteBuf buf, byte[][] md5s) {
        try {
            CompoundTag nbt = new CompoundTag(null);
            ListTag strList = new ListTag("md5s", ByteArrayTag.class);
            for (final byte[] md5 : md5s) {
                strList.add(new ByteArrayTag(null, md5));
            }
            nbt.put(strList);
            nbt.write(new DataOutputStream(new ByteBufOutputStream(buf)));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public byte[] stcDecode(ByteBuf buf) {
        try {
            CompoundTag nbt = new CompoundTag(null);
            nbt.read(new DataInputStream(new ByteBufInputStream(buf)));
            return ((ByteArrayTag) nbt.get("salt")).getValue();    
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
