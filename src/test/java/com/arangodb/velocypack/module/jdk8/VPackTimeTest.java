/*
 * DISCLAIMER
 *
 * Copyright 2016 ArangoDB GmbH, Cologne, Germany
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Copyright holder is ArangoDB GmbH, Cologne, Germany
 */

package com.arangodb.velocypack.module.jdk8;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import org.junit.BeforeClass;
import org.junit.Test;

import com.arangodb.velocypack.VPack;
import com.arangodb.velocypack.VPackBuilder;
import com.arangodb.velocypack.VPackSlice;
import com.arangodb.velocypack.ValueType;

/**
 * @author Mark Vollmary
 *
 */
public class VPackTimeTest {

	private static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");// ISO 8601

	private static VPack vp;

	@BeforeClass
	public static void setup() {
		vp = new VPack.Builder().registerModule(new VPackJdk8Module()).build();
	}

	protected static class TestEntityDate {
		private Instant instant;
		private LocalDate localDate;
		private LocalDateTime localDateTime;

		public TestEntityDate(final long millis) {
			super();
			instant = Instant.ofEpochMilli(millis);
			localDate = instant.atZone(ZoneId.systemDefault()).toLocalDate();
			localDateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
		}

		public TestEntityDate() {
			super();
		}

		public Instant getInstant() {
			return instant;
		}

		public void setInstant(final Instant instant) {
			this.instant = instant;
		}

		public LocalDate getLocalDate() {
			return localDate;
		}

		public void setLocalDate(final LocalDate localDate) {
			this.localDate = localDate;
		}

		public LocalDateTime getLocalDateTime() {
			return localDateTime;
		}

		public void setLocalDateTime(final LocalDateTime localDateTime) {
			this.localDateTime = localDateTime;
		}

	}

	@SuppressWarnings("deprecation")
	@Test
	public void serializeDate() {
		final VPackSlice vpack = vp.serialize(new TestEntityDate(1474988621));
		assertThat(vpack, is(notNullValue()));
		assertThat(vpack.isObject(), is(true));
		assertThat(vpack.get("instant").isString(), is(true));
		assertThat(vpack.get("instant").getAsString(), is(DATE_FORMAT.format(new Date(1474988621))));
		assertThat(vpack.get("localDate").isString(), is(true));
		assertThat(vpack.get("localDate").getAsString(), is(DATE_FORMAT.format(new Date(70, 0, 18))));
		assertThat(vpack.get("localDateTime").isString(), is(true));
		assertThat(vpack.get("localDateTime").getAsString(), is(DATE_FORMAT.format(new Date(1474988621))));
	}

	@SuppressWarnings("deprecation")
	@Test
	public void deserializeDate() {
		final VPackBuilder builder = new VPackBuilder();
		builder.add(ValueType.OBJECT);
		builder.add("instant", new Date(1475062216));
		builder.add("localDate", new Date(70, 0, 18));
		builder.add("localDateTime", new Date(1475062216));
		builder.close();

		final TestEntityDate entity = vp.deserialize(builder.slice(), TestEntityDate.class);
		assertThat(entity, is(notNullValue()));
		assertThat(entity.instant, is(Instant.ofEpochMilli(1475062216)));
		assertThat(entity.localDate, is(Instant.ofEpochMilli(1475062216).atZone(ZoneId.systemDefault()).toLocalDate()));
		assertThat(entity.localDateTime,
			is(LocalDateTime.ofInstant(Instant.ofEpochMilli(1475062216), ZoneId.systemDefault())));
	}

	@SuppressWarnings("deprecation")
	@Test
	public void deserializeDateFromString() {
		final VPackBuilder builder = new VPackBuilder();
		builder.add(ValueType.OBJECT);
		builder.add("instant", DATE_FORMAT.format(new Date(1475062216)));
		builder.add("localDate", DATE_FORMAT.format(new Date(70, 0, 18)));
		builder.add("localDateTime", DATE_FORMAT.format(new Date(1475062216)));
		builder.close();

		final TestEntityDate entity = vp.deserialize(builder.slice(), TestEntityDate.class);
		assertThat(entity, is(notNullValue()));
		assertThat(entity.instant, is(Instant.ofEpochMilli(1475062216)));
		assertThat(entity.localDate, is(Instant.ofEpochMilli(1475062216).atZone(ZoneId.systemDefault()).toLocalDate()));
		assertThat(entity.localDateTime,
			is(LocalDateTime.ofInstant(Instant.ofEpochMilli(1475062216), ZoneId.systemDefault())));
	}

	@Test
	public void date() {
		final TestEntityDate entity = new TestEntityDate(1474988621);
		final VPackSlice vpack = vp.serialize(entity);
		assertThat(vpack, is(notNullValue()));
		final TestEntityDate entity2 = vp.deserialize(vpack, TestEntityDate.class);
		assertThat(entity2, is(notNullValue()));
		assertThat(entity2.instant, is(entity.instant));
		assertThat(entity2.localDate, is(entity.localDate));
		assertThat(entity2.localDateTime, is(entity.localDateTime));
	}

}
