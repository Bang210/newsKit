import React, { useEffect, useState } from 'react';

const Home = () => {
  const [keywords, setKeywords] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [createdTime, setCreatedTime] = useState(null);

  useEffect(() => {
    const fetchData = async () => {
      try {
        const response = await fetch('http://localhost:8080/keyword/dataRequest', {
            method: 'POST',
            credentials: 'include',
        });
        const result = await response.json();

        if (result.resultCode === "200") {
          setKeywords(result.data.keywordList);
          setCreatedTime(result.data.createdTime);
        } else {
          setError(result.msg);
        }
      } catch (err) {
        setError('데이터를 가져오는 중 오류가 발생했습니다.');
      } finally {
        setLoading(false);
      }
    };

    fetchData();
  }, []);

  const formatDateTime = (dateTimeString) => {
    const date = new Date(dateTimeString);
    const options = {
      year: 'numeric',
      month: '2-digit',
      day: '2-digit',
      hour: '2-digit',
      minute: '2-digit',
      second: '2-digit',
      hour12: false, // 24시간 형식
    };
    const formattedDate = date.toLocaleString('ko-KR', options);
    return formattedDate.replace(/\/|,/g, ' ').replace(/\s+/g, ' ').trim(); // 형식 맞추기
  };

  if (loading) {
    return <p>로딩 중...</p>;
  }

  if (error) {
    return <p>{error}</p>;
  }

  return (
    <div>
      <div>
        {createdTime && <><p className="text-2xl font-bold">{formatDateTime(createdTime)}</p><p> 기준 가장 많이 언급된 키워드입니다.</p></>}
      </div>
      <ul>
        {keywords.map((keyword, index) => (
          <li key={index}><div className="collapse collapse-plus bg-base-200 my-3">
          <input type="radio" name="my-accordion-3" defaultChecked />
          <div className="collapse-title text-xl font-medium">{keyword}</div>
          <div className="collapse-content">
            <p>child keywords of {keyword}</p>
          </div>
        </div></li>
        ))}
      </ul>
    </div>
  );
};

export default Home;