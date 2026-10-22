package com.naterbobber.darkerdepths.common.init;

import com.mojang.serialization.Codec;
import com.naterbobber.darkerdepths.DarkerDepths;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class DDDataAttachments {
    public static final DeferredRegister<AttachmentType<?>> ATTACHMENTS =
            DeferredRegister.create(NeoForgeRegistries.ATTACHMENT_TYPES, DarkerDepths.MOD_ID);

    public static final DeferredHolder<AttachmentType<?>, AttachmentType<Set<String>>> PENDING_PROCESSORS =
            ATTACHMENTS.register("pending_chunk_processors", () -> AttachmentType.<Set<String>>builder(() -> new HashSet<>())
                    .serialize(Codec.STRING.listOf().xmap(HashSet::new, ArrayList::new))
                    .build());
}
