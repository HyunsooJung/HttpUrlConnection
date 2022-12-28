package com.hyunsu.http;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AconData {
    @SerializedName("name")
    @Expose
    String name;

    @SerializedName("rank")
    @Expose
    String rank;

    @SerializedName("time")
    @Expose
    String time;

}
