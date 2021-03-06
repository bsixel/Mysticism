package com.bsixel.mysticism.common.networking;

import com.bsixel.mysticism.MysticismMod;
import com.bsixel.mysticism.common.networking.packets.*;
import net.minecraftforge.fml.network.NetworkRegistry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class NetworkManager {
    private static final Logger logger = LogManager.getLogger(MysticismMod.MOD_ID);
    private static final String protocol_version = "0";

    private static int packetId = 0;

    public static MysticismChannel channel;

    public static boolean register() {
        logger.info("Registering network packets and initializing channel!");
        channel = new MysticismChannel(NetworkRegistry.newSimpleChannel(MysticismMod.NET_CHANNEL, () -> protocol_version, protocol_version::equals, protocol_version::equals));

        channel.getChannel().registerMessage(packetId++, MysticismManaPacket.class, MysticismManaPacket::encode, MysticismManaPacket::decode, MysticismManaPacket::handle);
        channel.getChannel().registerMessage(packetId++, MysticismCastSpellPacket.class, MysticismCastSpellPacket::encode, MysticismCastSpellPacket::decode, MysticismCastSpellPacket::handle);
        channel.getChannel().registerMessage(packetId++, MysticismSpellcasterPacketFromServer.class, MysticismSpellcasterPacketFromServer::encode, MysticismSpellcasterPacketFromServer::decode, MysticismSpellcasterPacketFromServer::handle);
        channel.getChannel().registerMessage(packetId++, MysticismSpellcasterPacketFromClient.class, MysticismSpellcasterPacketFromClient::encode, MysticismSpellcasterPacketFromClient::decode, MysticismSpellcasterPacketFromClient::handle);
        channel.getChannel().registerMessage(packetId++, GenericCompoundPacket.class, GenericCompoundPacket::encode, GenericCompoundPacket::decode, GenericCompoundPacket::handle);
        channel.getChannel().registerMessage(packetId++, MysticismClientChangedSpellPacket.class, MysticismClientChangedSpellPacket::encode, MysticismClientChangedSpellPacket::decode, MysticismClientChangedSpellPacket::handle);

        return true; // I'd like to know if this succeeded fully
    }

}
