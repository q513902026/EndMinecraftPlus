package luohuayu.ACProtocol;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;

import org.spacehq.opennbt.tag.builtin.ByteArrayTag;
import org.spacehq.opennbt.tag.builtin.ByteTag;
import org.spacehq.opennbt.tag.builtin.CompoundTag;
import org.spacehq.opennbt.tag.builtin.ListTag;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.ByteBufOutputStream;

public class AnotherStarAntiCheat {
    private final RSAPublicKeySpec clientPublicKey;
    private final RSAPrivateKeySpec clientPrivateKey;
    private final RSAPrivateKeySpec serverPrivateKey;

    {
        clientPublicKey = new RSAPublicKeySpec(new BigInteger("90582577094276665384804297579612222028352858965734430918359338883687022181407192388552108083469539909657977600363535513080606600149709934508846296230201104157618580827253874746853403252876192365582460562549171049457092849469390003409874701599243754874843344932806030539695092275477654361631673011953560268683"), new BigInteger("65537"));
        clientPrivateKey = new RSAPrivateKeySpec(new BigInteger("127248525424337605798857856487221856110381008926245852065442613615516536646091779977965275117600642459175520164216428670736796699063602255494216811030412652871788996579589741447673977696054201776961304071023003222194917084082537727766958565979898764069468089819871479999657703456269930991526213852540524234013"), new BigInteger("100929747297067571366346172328894617152598159909732088470113644826003335641502401800732272321942057102291253630411803945286481926403774546364654748782869361445361439507584335938774171558129265029774065511640383438744673112463513326659968190050872802016576114878437091340677423733487140074565054831599514935681"));
        serverPrivateKey = new RSAPrivateKeySpec(new BigInteger("90582577094276665384804297579612222028352858965734430918359338883687022181407192388552108083469539909657977600363535513080606600149709934508846296230201104157618580827253874746853403252876192365582460562549171049457092849469390003409874701599243754874843344932806030539695092275477654361631673011953560268683"), new BigInteger("53164759264710803577009892219658881552719726568457129335714054200848700859176154770979093297384009836046723353189547789960564763030938136180825682965266715437551259363202056897867941846985027308929393795178680964219322204389989883292612698672592212090988584189302073745814770265916444742576632864448430325233"));
    }

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
