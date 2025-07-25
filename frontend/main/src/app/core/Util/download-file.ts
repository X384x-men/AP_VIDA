
const downloadCSV = (name: string, data: any[]) => {
  const object = Array.isArray(data) ? data[0] : data;
  const headers = Object.keys(object).join(',');
  let values = Object.values(object).join(',');
  if (Array.isArray(data)) {
    const moreValues = [];
    data.forEach( row => {
      moreValues.push(Object.values(row).join(','));
    })
    values = moreValues.join('\n');
  }
  const dataString = [headers, '\n', values];
  downloadFile('.csv', dataString, name)
}

const downloadFile = (type: string, data: any, filename: string) => {
  const blob = new Blob(data, {type: 'text/' + type});
  const url = URL.createObjectURL(blob);
  const link = document.createElement('a');
  link.href = url;
  link.download = filename + type;
  link.click();

}

export {
  downloadCSV
}
