/*
 * Copyright (c) 2013 Pantheon Technologies s.r.o. and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.openflowjava.protocol.impl.util;

import io.netty.buffer.ByteBuf;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.opendaylight.yang.gen.v1.urn.opendaylight.openflow.augments.rev131002.EthertypeAction;
import org.opendaylight.yang.gen.v1.urn.opendaylight.openflow.augments.rev131002.GroupIdAction;
import org.opendaylight.yang.gen.v1.urn.opendaylight.openflow.augments.rev131002.MaxLengthAction;
import org.opendaylight.yang.gen.v1.urn.opendaylight.openflow.augments.rev131002.MplsTtlAction;
import org.opendaylight.yang.gen.v1.urn.opendaylight.openflow.augments.rev131002.NwTtlAction;
import org.opendaylight.yang.gen.v1.urn.opendaylight.openflow.augments.rev131002.OxmFieldsAction;
import org.opendaylight.yang.gen.v1.urn.opendaylight.openflow.augments.rev131002.PortAction;
import org.opendaylight.yang.gen.v1.urn.opendaylight.openflow.augments.rev131002.PortNumberMatchEntry;
import org.opendaylight.yang.gen.v1.urn.opendaylight.openflow.augments.rev131002.QueueIdAction;
import org.opendaylight.yang.gen.v1.urn.opendaylight.openflow.common.action.rev130731.actions.ActionsList;
import org.opendaylight.yang.gen.v1.urn.opendaylight.openflow.oxm.rev130731.oxm.fields.MatchEntries;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author michal.polkorab
 *
 */
public class ActionsDeserializerTest {

    private static final Logger LOGGER = LoggerFactory
            .getLogger(ActionsDeserializerTest.class);
    /**
     * Testing actions deserialization
     */
    @Test
    public void test() {
        ByteBuf message = BufferHelper.buildBuffer("00 00 00 10 00 00 00 01 00 02 00 00 00 00 00 00 "
                + "00 0B 00 08 00 00 00 00 "
                + "00 0C 00 08 00 00 00 00 "
                + "00 0F 00 08 03 00 00 00 "
                + "00 10 00 08 00 00 00 00 "
                + "00 11 00 08 00 04 00 00 "
                + "00 12 00 08 00 00 00 00 "
                + "00 13 00 08 00 05 00 00 "
                + "00 14 00 08 00 06 00 00 "
                + "00 15 00 08 00 00 00 07 "
                + "00 16 00 08 00 00 00 08 "
                + "00 17 00 08 09 00 00 00 "
                + "00 18 00 08 00 00 00 00 "
                + "00 19 00 10 80 00 02 04 00 00 00 0B 00 00 00 00 "
                + "00 1A 00 08 00 0A 00 00 "
                + "00 1B 00 08 00 00 00 00");
        
        message.skipBytes(4); // skip XID
        LOGGER.info("bytes: " + message.readableBytes());
        List<ActionsList> actions = ActionsDeserializer.createActionsList(message, message.readableBytes());
        Assert.assertEquals("Wrong action type", "org.opendaylight.yang.gen.v1.urn.opendaylight."
                + "openflow.common.action.rev130731.Output", actions.get(0).getAction().getType().getName());
        Assert.assertEquals("Wrong action port", 1,
                actions.get(0).getAction().getAugmentation(PortAction.class).getPort().getValue().intValue());
        Assert.assertEquals("Wrong action max-length", 2,
                actions.get(0).getAction().getAugmentation(MaxLengthAction.class).getMaxLength().intValue());
        Assert.assertEquals("Wrong action type", "org.opendaylight.yang.gen.v1.urn.opendaylight."
                + "openflow.common.action.rev130731.CopyTtlOut", actions.get(1).getAction().getType().getName());
        Assert.assertEquals("Wrong action type", "org.opendaylight.yang.gen.v1.urn.opendaylight."
                + "openflow.common.action.rev130731.CopyTtlIn", actions.get(2).getAction().getType().getName());
        Assert.assertEquals("Wrong action type", "org.opendaylight.yang.gen.v1.urn.opendaylight."
                + "openflow.common.action.rev130731.SetMplsTtl", actions.get(3).getAction().getType().getName());
        Assert.assertEquals("Wrong action value", 3,
                actions.get(3).getAction().getAugmentation(MplsTtlAction.class).getMplsTtl().shortValue());
        Assert.assertEquals("Wrong action type", "org.opendaylight.yang.gen.v1.urn.opendaylight."
                + "openflow.common.action.rev130731.DecMplsTtl", actions.get(4).getAction().getType().getName());
        Assert.assertEquals("Wrong action type", "org.opendaylight.yang.gen.v1.urn.opendaylight."
                + "openflow.common.action.rev130731.PushVlan", actions.get(5).getAction().getType().getName());
        Assert.assertEquals("Wrong action value", 4,
                actions.get(5).getAction().getAugmentation(EthertypeAction.class).getEthertype().getValue().intValue());
        Assert.assertEquals("Wrong action type", "org.opendaylight.yang.gen.v1.urn.opendaylight."
                + "openflow.common.action.rev130731.PopVlan", actions.get(6).getAction().getType().getName());
        Assert.assertEquals("Wrong action type", "org.opendaylight.yang.gen.v1.urn.opendaylight."
                + "openflow.common.action.rev130731.PushMpls", actions.get(7).getAction().getType().getName());
        Assert.assertEquals("Wrong action value", 5,
                actions.get(7).getAction().getAugmentation(EthertypeAction.class).getEthertype().getValue().intValue());
        Assert.assertEquals("Wrong action type", "org.opendaylight.yang.gen.v1.urn.opendaylight."
                + "openflow.common.action.rev130731.PopMpls", actions.get(8).getAction().getType().getName());
        Assert.assertEquals("Wrong action value", 6,
                actions.get(8).getAction().getAugmentation(EthertypeAction.class).getEthertype().getValue().intValue());
        Assert.assertEquals("Wrong action type", "org.opendaylight.yang.gen.v1.urn.opendaylight."
                + "openflow.common.action.rev130731.SetQueue", actions.get(9).getAction().getType().getName());
        Assert.assertEquals("Wrong action value", 7,
                actions.get(9).getAction().getAugmentation(QueueIdAction.class).getQueueId().intValue());
        Assert.assertEquals("Wrong action type", "org.opendaylight.yang.gen.v1.urn.opendaylight."
                + "openflow.common.action.rev130731.Group", actions.get(10).getAction().getType().getName());
        Assert.assertEquals("Wrong action value", 8,
                actions.get(10).getAction().getAugmentation(GroupIdAction.class).getGroupId().intValue());
        Assert.assertEquals("Wrong action type", "org.opendaylight.yang.gen.v1.urn.opendaylight."
                + "openflow.common.action.rev130731.SetNwTtl", actions.get(11).getAction().getType().getName());
        Assert.assertEquals("Wrong action value", 9,
                actions.get(11).getAction().getAugmentation(NwTtlAction.class).getNwTtl().intValue());
        Assert.assertEquals("Wrong action type", "org.opendaylight.yang.gen.v1.urn.opendaylight."
                + "openflow.common.action.rev130731.DecNwTtl", actions.get(12).getAction().getType().getName());
        Assert.assertEquals("Wrong action type", "org.opendaylight.yang.gen.v1.urn.opendaylight."
                + "openflow.common.action.rev130731.SetField", actions.get(13).getAction().getType().getName());
        List<MatchEntries> entries = actions.get(13).getAction().getAugmentation(OxmFieldsAction.class).getMatchEntries();
        Assert.assertEquals("Wrong number of fields", 1, entries.size());
        Assert.assertEquals("Wrong match entry class", "org.opendaylight.yang.gen.v1.urn.opendaylight.openflow."
                + "oxm.rev130731.OpenflowBasicClass", entries.get(0).getOxmClass().getName());
        Assert.assertEquals("Wrong match entry field", "org.opendaylight.yang.gen.v1.urn.opendaylight.openflow."
                + "oxm.rev130731.InPhyPort", entries.get(0).getOxmMatchField().getName());
        Assert.assertEquals("Wrong match entry mask", false, entries.get(0).isHasMask());
        Assert.assertEquals("Wrong match entry value", 11, 
                entries.get(0).getAugmentation(PortNumberMatchEntry.class).getPortNumber().getValue().intValue());
        Assert.assertEquals("Wrong action type", "org.opendaylight.yang.gen.v1.urn.opendaylight."
                + "openflow.common.action.rev130731.PushPbb", actions.get(14).getAction().getType().getName());
        Assert.assertEquals("Wrong action value", 10,
                actions.get(14).getAction().getAugmentation(EthertypeAction.class).getEthertype().getValue().intValue());
        Assert.assertEquals("Wrong action type", "org.opendaylight.yang.gen.v1.urn.opendaylight."
                + "openflow.common.action.rev130731.PopPbb", actions.get(15).getAction().getType().getName());
        Assert.assertTrue("Unread data in message", message.readableBytes() == 0);
    }

}