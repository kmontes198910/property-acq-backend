package com.kynsof.treatments.application.query.result.GetResultsByExternalConsultation;

import com.kynsof.share.core.domain.bus.query.IResponse;
import com.kynsof.treatments.application.query.result.ResultResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResultResponseByExternalConsult implements IResponse {
  private List<ResultResponse> results;
}