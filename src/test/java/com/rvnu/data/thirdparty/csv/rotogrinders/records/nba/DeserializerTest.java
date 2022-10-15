package com.rvnu.data.thirdparty.csv.rotogrinders.records.nba;

import com.rvnu.models.thirdparty.awesomeo.nba.Position;
import com.rvnu.models.thirdparty.iso.NaturalNumber;
import com.rvnu.models.thirdparty.iso.PositiveInteger;
import com.rvnu.models.thirdparty.money.NonNegativeDollars;
import com.rvnu.models.thirdparty.nba.Team;
import com.rvnu.models.thirdparty.rotogrinders.nba.Projection;
import com.rvnu.models.thirdparty.strings.NonEmptyString;
import junit.framework.TestCase;

import java.io.ByteArrayInputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;

public class DeserializerTest extends TestCase {

    public void test() {
        try {
            Deserializer
                    .getInstance()
                    .deserialize(
                            new ByteArrayInputStream(
                                    ("""
                                            player_id,team,opp,pos,name,fpts,proj_own,smash,minutes,ceil,floor,min_exposure,max_exposure,rg_value,salary,custom,rg_id,partner_id
                                            457688,SAS,MIL,PF,Al-Farouq Aminu,0,,0,,,,0,,-19.50,3000,0,1279,457688
                                            """).getBytes(StandardCharsets.UTF_8)
                            ),
                            (projection -> {
                                try {
                                    assertEquals(
                                            new Projection(
                                                    new PositiveInteger(457688),
                                                    Team.SAN_ANTONIO_SPURS,
                                                    Team.MILWAUKEE_BUCKS,
                                                    Position.POWER_FORWARD,
                                                    new NonEmptyString("Al-Farouq Aminu"),
                                                    new BigDecimal("0"),
                                                    new NonNegativeDollars(new NaturalNumber(new BigInteger("3000").longValue())),
                                                    new PositiveInteger(1279),
                                                    new PositiveInteger(457688)
                                            ),
                                            projection
                                    );
                                } catch (NonEmptyString.ValueMustNotBeEmpty | NaturalNumber.ValueMustNotBeNegative | PositiveInteger.ValueMustBePositive e) {
                                    throw new RuntimeException("unexpected", e);
                                }
                            })
                    );
        } catch (com.rvnu.data.firstparty.csv.records.interfaces.Deserializer.UnableToDeserializeRecords e) {
            throw new RuntimeException("unexpected", e);
        }
    }
}